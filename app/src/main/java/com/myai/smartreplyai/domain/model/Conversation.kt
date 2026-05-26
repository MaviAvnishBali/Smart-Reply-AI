package com.myai.smartreplyai.domain.model

data class Conversation(
    val id: Long,
    val senderName: String,
    val senderKey: String,
    val isGroup: Boolean,
    val lastMessage: String,
    val lastMessageTime: Long,
    val unreadCount: Int,
    val leadType: LeadType,
    val packageName: String
)
