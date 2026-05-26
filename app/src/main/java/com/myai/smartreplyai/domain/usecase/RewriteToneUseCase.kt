package com.myai.smartreplyai.domain.usecase

import com.myai.smartreplyai.domain.model.ReplyTone
import com.myai.smartreplyai.domain.repository.SettingsRepository
import com.myai.smartreplyai.features.ai.provider.GeminiProvider
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RewriteToneUseCase @Inject constructor(
    private val geminiProvider: GeminiProvider,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(text: String, tone: ReplyTone): String {
        val settings = settingsRepository.userSettings.first()
        if (settings.geminiApiKey.isBlank()) return text
        settingsRepository.resetDailyUsageIfNeeded()
        if (!settingsRepository.incrementAiUsage()) return text
        geminiProvider.apiKey = settings.geminiApiKey
        geminiProvider.model = settings.geminiModel
        return geminiProvider.rewriteTone(text, tone, settings.languagePreference)
    }
}
