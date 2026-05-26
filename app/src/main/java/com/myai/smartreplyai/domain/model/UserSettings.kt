package com.myai.smartreplyai.domain.model

data class UserSettings(
    val geminiApiKey: String,
    val geminiModel: String,
    val customSystemPrompt: String,
    val overlayEnabled: Boolean,
    val darkMode: Boolean?,
    val languagePreference: String,
    val isPremium: Boolean,
    val dailyAiUsage: Int,
    val dailyAiLimit: Int
)
