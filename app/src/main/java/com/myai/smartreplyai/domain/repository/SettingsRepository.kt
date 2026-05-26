package com.myai.smartreplyai.domain.repository

import com.myai.smartreplyai.domain.model.PremiumEntitlement
import com.myai.smartreplyai.domain.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val userSettings: Flow<UserSettings>
    val onboardingComplete: Flow<Boolean>
    val premiumEntitlement: Flow<PremiumEntitlement>
    suspend fun setOnboardingComplete(complete: Boolean)
    suspend fun saveGeminiApiKey(key: String)
    suspend fun saveGeminiModel(model: String)
    suspend fun saveCustomPrompt(prompt: String)
    suspend fun setOverlayEnabled(enabled: Boolean)
    suspend fun setDarkMode(enabled: Boolean?)
    suspend fun setLanguage(language: String)
    suspend fun setPremiumActive(active: Boolean)
    suspend fun incrementAiUsage(): Boolean
    suspend fun resetDailyUsageIfNeeded()
    suspend fun grantRewardedBonus(replies: Int)
}
