package com.myai.smartreplyai.data.repository

import com.myai.smartreplyai.core.constants.AppConstants
import com.myai.smartreplyai.core.utils.DateTimeUtils
import com.myai.smartreplyai.data.local.PreferencesDataStore
import com.myai.smartreplyai.data.local.dao.UserSettingsDao
import com.myai.smartreplyai.data.local.entity.UserSettingsEntity
import com.myai.smartreplyai.domain.model.PremiumEntitlement
import com.myai.smartreplyai.domain.model.UserSettings
import com.myai.smartreplyai.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val preferences: PreferencesDataStore,
    private val userSettingsDao: UserSettingsDao
) : SettingsRepository {

    override val onboardingComplete: Flow<Boolean> = preferences.onboardingComplete

    override val userSettings: Flow<UserSettings> = combine(
        combine(
            preferences.geminiApiKey,
            preferences.geminiModel,
            preferences.customPrompt,
            preferences.overlayEnabled,
            preferences.darkMode
        ) { apiKey, model, prompt, overlay, dark ->
            arrayOf(apiKey, model, prompt, overlay, dark)
        },
        combine(
            preferences.languagePreference,
            preferences.isPremium,
            preferences.dailyAiUsage
        ) { language, premium, usage ->
            arrayOf(language, premium, usage)
        }
    ) { first, second ->
        val isPremium = second[1] as Boolean
        UserSettings(
            geminiApiKey = first[0] as String,
            geminiModel = first[1] as String,
            customSystemPrompt = first[2] as String,
            overlayEnabled = first[3] as Boolean,
            darkMode = first[4] as Boolean?,
            languagePreference = second[0] as String,
            isPremium = isPremium,
            dailyAiUsage = second[2] as Int,
            dailyAiLimit = if (isPremium) {
                AppConstants.PREMIUM_DAILY_AI_REPLIES
            } else {
                AppConstants.FREE_DAILY_AI_REPLIES
            }
        )
    }

    override val premiumEntitlement: Flow<PremiumEntitlement> = userSettings.map { settings ->
        PremiumEntitlement(
            isPremium = settings.isPremium,
            dailyAiLimit = settings.dailyAiLimit,
            dailyAiUsed = settings.dailyAiUsage,
            canUseOverlay = settings.isPremium,
            canUseToneRewrite = true,
            canUseVoiceReply = true,
            canUseLeadDetection = true
        )
    }

    override suspend fun setOnboardingComplete(complete: Boolean) {
        preferences.setOnboardingComplete(complete)
    }

    override suspend fun saveGeminiApiKey(key: String) {
        preferences.setGeminiApiKey(key)
        syncRoomSettings { it.copy(geminiApiKey = key) }
    }

    override suspend fun saveGeminiModel(model: String) {
        preferences.setGeminiModel(model)
        syncRoomSettings { it.copy(geminiModel = model) }
    }

    override suspend fun saveCustomPrompt(prompt: String) {
        preferences.setCustomPrompt(prompt)
        syncRoomSettings { it.copy(customSystemPrompt = prompt) }
    }

    override suspend fun setOverlayEnabled(enabled: Boolean) {
        preferences.setOverlayEnabled(enabled)
        syncRoomSettings { it.copy(overlayEnabled = enabled) }
    }

    override suspend fun setDarkMode(enabled: Boolean?) {
        preferences.setDarkMode(enabled)
    }

    override suspend fun setLanguage(language: String) {
        preferences.setLanguage(language)
        syncRoomSettings { it.copy(languagePreference = language) }
    }

    override suspend fun setPremiumActive(active: Boolean) {
        preferences.setPremium(active)
    }

    override suspend fun incrementAiUsage(): Boolean {
        resetDailyUsageIfNeeded()
        val settings = userSettings.first()
        if (settings.dailyAiUsage >= settings.dailyAiLimit) return false
        preferences.setDailyAiUsage(settings.dailyAiUsage + 1)
        return true
    }

    override suspend fun resetDailyUsageIfNeeded() {
        val today = DateTimeUtils.todayKey()
        val lastDate = preferences.lastUsageDate.first()
        if (lastDate != today) {
            preferences.setDailyAiUsage(0)
            preferences.setLastUsageDate(today)
        }
    }

    override suspend fun grantRewardedBonus(replies: Int) {
        val currentUsage = preferences.dailyAiUsage.first()
        preferences.setDailyAiUsage(maxOf(0, currentUsage - replies))
    }

    private suspend fun syncRoomSettings(transform: (UserSettingsEntity) -> UserSettingsEntity) {
        val current = userSettingsDao.get() ?: UserSettingsEntity()
        userSettingsDao.insert(transform(current))
    }
}
