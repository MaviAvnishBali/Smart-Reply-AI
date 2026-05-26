package com.myai.smartreplyai.data.mapper

import com.myai.smartreplyai.data.local.entity.ConversationEntity
import com.myai.smartreplyai.domain.model.Conversation
import com.myai.smartreplyai.domain.model.LeadType

object ConversationMapper {
    fun toDomain(entity: ConversationEntity): Conversation = Conversation(
        id = entity.id,
        senderName = entity.senderName,
        senderKey = entity.senderKey,
        isGroup = entity.isGroup,
        lastMessage = entity.lastMessage,
        lastMessageTime = entity.lastMessageTime,
        unreadCount = entity.unreadCount,
        leadType = LeadType.valueOf(entity.leadType),
        packageName = entity.packageName
    )

    fun toEntity(domain: Conversation): ConversationEntity = ConversationEntity(
        id = domain.id,
        senderName = domain.senderName,
        senderKey = domain.senderKey,
        isGroup = domain.isGroup,
        lastMessage = domain.lastMessage,
        lastMessageTime = domain.lastMessageTime,
        unreadCount = domain.unreadCount,
        leadType = domain.leadType.name,
        packageName = domain.packageName
    )
}
