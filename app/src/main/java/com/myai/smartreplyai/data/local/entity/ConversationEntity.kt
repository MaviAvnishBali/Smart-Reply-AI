package com.myai.smartreplyai.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "conversations",
    indices = [Index(value = ["senderKey"], unique = true)]
)
data class ConversationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val senderName: String,
    val senderKey: String,
    val isGroup: Boolean,
    val lastMessage: String,
    val lastMessageTime: Long,
    val unreadCount: Int,
    val leadType: String,
    val packageName: String
)
