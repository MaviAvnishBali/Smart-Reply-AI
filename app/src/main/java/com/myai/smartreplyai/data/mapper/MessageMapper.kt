package com.myai.smartreplyai.data.mapper

import com.myai.smartreplyai.data.local.entity.MessageEntity
import com.myai.smartreplyai.domain.model.Message

object MessageMapper {
    fun toDomain(entity: MessageEntity): Message = Message(
        id = entity.id,
        conversationId = entity.conversationId,
        content = entity.content,
        senderName = entity.senderName,
        isIncoming = entity.isIncoming,
        timestamp = entity.timestamp,
        notificationKey = entity.notificationKey,
        isEdited = entity.isEdited
    )

    fun toEntity(domain: Message): MessageEntity = MessageEntity(
        id = domain.id,
        conversationId = domain.conversationId,
        content = domain.content,
        senderName = domain.senderName,
        isIncoming = domain.isIncoming,
        timestamp = domain.timestamp,
        notificationKey = domain.notificationKey,
        isEdited = domain.isEdited
    )
}
