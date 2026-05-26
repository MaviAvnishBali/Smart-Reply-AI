package com.myai.smartreplyai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val geminiApiKey: String = "",
    val geminiModel: String = "gemini-2.0-flash",
    val customSystemPrompt: String = "",
    val overlayEnabled: Boolean = false,
    val languagePreference: String = "en"
)
