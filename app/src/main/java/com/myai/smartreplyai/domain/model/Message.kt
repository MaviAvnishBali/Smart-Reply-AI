package com.myai.smartreplyai.domain.model

data class Message(
    val id: Long,
    val conversationId: Long,
    val content: String,
    val senderName: String,
    val isIncoming: Boolean,
    val timestamp: Long,
    val notificationKey: String,
    val isEdited: Boolean
)
