package com.myai.smartreplyai.domain.repository

import com.myai.smartreplyai.domain.model.Conversation
import com.myai.smartreplyai.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {
    fun observeConversations(): Flow<List<Conversation>>
    fun observeConversation(id: Long): Flow<Conversation?>
    fun observeMessages(conversationId: Long): Flow<List<Message>>
    suspend fun getConversationBySenderKey(senderKey: String): Conversation?
    suspend fun saveIncomingMessage(
        senderName: String,
        senderKey: String,
        content: String,
        isGroup: Boolean,
        packageName: String,
        notificationKey: String,
        timestamp: Long,
        isEdited: Boolean
    ): Pair<Long, Long>
    suspend fun markAsRead(conversationId: Long)
}
