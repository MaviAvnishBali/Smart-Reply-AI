package com.myai.smartreplyai.data.repository

import com.myai.smartreplyai.data.local.dao.ConversationDao
import com.myai.smartreplyai.data.local.dao.MessageDao
import com.myai.smartreplyai.data.local.entity.ConversationEntity
import com.myai.smartreplyai.data.local.entity.MessageEntity
import com.myai.smartreplyai.data.mapper.ConversationMapper
import com.myai.smartreplyai.data.mapper.MessageMapper
import com.myai.smartreplyai.domain.model.Conversation
import com.myai.smartreplyai.domain.model.LeadType
import com.myai.smartreplyai.domain.model.Message
import com.myai.smartreplyai.domain.repository.AnalyticsRepository
import com.myai.smartreplyai.domain.repository.ConversationRepository
import com.myai.smartreplyai.features.ai.lead.LeadDetector
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationRepositoryImpl @Inject constructor(
    private val conversationDao: ConversationDao,
    private val messageDao: MessageDao,
    private val leadDetector: LeadDetector,
    private val analyticsRepository: AnalyticsRepository
) : ConversationRepository {

    override fun observeConversations(): Flow<List<Conversation>> =
        conversationDao.observeAll().map { list -> list.map(ConversationMapper::toDomain) }

    override fun observeConversation(id: Long): Flow<Conversation?> =
        conversationDao.observeById(id).map { it?.let(ConversationMapper::toDomain) }

    override fun observeMessages(conversationId: Long): Flow<List<Message>> =
        messageDao.observeByConversation(conversationId)
            .map { list -> list.map(MessageMapper::toDomain) }

    override suspend fun getConversationBySenderKey(senderKey: String): Conversation? =
        conversationDao.getBySenderKey(senderKey)?.let(ConversationMapper::toDomain)

    override suspend fun saveIncomingMessage(
        senderName: String,
        senderKey: String,
        content: String,
        isGroup: Boolean,
        packageName: String,
        notificationKey: String,
        timestamp: Long,
        isEdited: Boolean
    ): Pair<Long, Long> {
        val existingMessage = messageDao.getByNotificationKey(notificationKey)
        val leadType = leadDetector.detect(content)

        if (existingMessage != null) {
            val updated = existingMessage.copy(
                content = content,
                isEdited = isEdited || existingMessage.isEdited,
                timestamp = timestamp
            )
            messageDao.update(updated)
            updateConversationSummary(
                conversationId = existingMessage.conversationId,
                senderName = senderName,
                content = content,
                timestamp = timestamp,
                leadType = leadType
            )
            analyticsRepository.logCommonQuestion(content.take(120))
            return existingMessage.conversationId to existingMessage.id
        }

        val existingConversation = conversationDao.getBySenderKey(senderKey)
        val conversationId = if (existingConversation == null) {
            conversationDao.insert(
                ConversationEntity(
                    senderName = senderName,
                    senderKey = senderKey,
                    isGroup = isGroup,
                    lastMessage = content,
                    lastMessageTime = timestamp,
                    unreadCount = 1,
                    leadType = leadType.name,
                    packageName = packageName
                )
            )
        } else {
            val updated = existingConversation.copy(
                senderName = senderName,
                lastMessage = content,
                lastMessageTime = timestamp,
                unreadCount = existingConversation.unreadCount + 1,
                leadType = if (leadType != LeadType.NONE) {
                    leadType.name
                } else {
                    existingConversation.leadType
                }
            )
            conversationDao.update(updated)
            existingConversation.id
        }

        val messageId = messageDao.insert(
            MessageEntity(
                conversationId = conversationId,
                content = content,
                senderName = senderName,
                isIncoming = true,
                timestamp = timestamp,
                notificationKey = notificationKey,
                isEdited = isEdited
            )
        )
        analyticsRepository.logCommonQuestion(content.take(120))
        return conversationId to messageId
    }

    override suspend fun markAsRead(conversationId: Long) {
        conversationDao.markAsRead(conversationId)
    }

    private suspend fun updateConversationSummary(
        conversationId: Long,
        senderName: String,
        content: String,
        timestamp: Long,
        leadType: LeadType
    ) {
        val conversation = conversationDao.getById(conversationId) ?: return
        conversationDao.update(
            conversation.copy(
                senderName = senderName,
                lastMessage = content,
                lastMessageTime = timestamp,
                leadType = if (leadType != LeadType.NONE) {
                    leadType.name
                } else {
                    conversation.leadType
                }
            )
        )
    }
}
