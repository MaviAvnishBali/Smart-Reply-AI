package com.myai.smartreplyai.features.ai.engine

import com.myai.smartreplyai.core.constants.AppConstants
import com.myai.smartreplyai.data.local.dao.MessageDao
import com.myai.smartreplyai.data.repository.AnalyticsRepositoryImpl
import com.myai.smartreplyai.domain.model.SmartReplySuggestion
import com.myai.smartreplyai.domain.model.SuggestionSource
import com.myai.smartreplyai.domain.repository.SettingsRepository
import com.myai.smartreplyai.domain.repository.TemplateRepository
import com.myai.smartreplyai.features.ai.provider.AIProvider
import com.myai.smartreplyai.features.ai.provider.GeminiProvider
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmartReplyEngine @Inject constructor(
    private val templateRepository: TemplateRepository,
    private val settingsRepository: SettingsRepository,
    private val messageDao: MessageDao,
    private val geminiProvider: GeminiProvider,
    private val analyticsRepository: AnalyticsRepositoryImpl
) {
    private val keywordReplies = mapOf(
        "hello" to "Hello! How can I help you today?",
        "hi" to "Hi there! Thanks for messaging. How may I assist?",
        "namaste" to "Namaste! Aapki kaise madad kar sakta hoon?",
        "thanks" to "You're welcome! Let me know if you need anything else.",
        "ok" to "Got it! 👍",
        "price" to "I'll share our pricing details shortly. One moment please!"
    )

    suspend fun generateSuggestions(
        conversationId: Long,
        incomingMessage: String
    ): List<SmartReplySuggestion> {
        val suggestions = mutableListOf<SmartReplySuggestion>()

        val templates = templateRepository.findMatchingTemplates(incomingMessage)
        templates.forEach { template ->
            suggestions.add(
                SmartReplySuggestion(
                    text = template.content,
                    source = SuggestionSource.TEMPLATE
                )
            )
            templateRepository.incrementUsage(template.id)
            analyticsRepository.logTemplateUsed()
        }
        if (suggestions.size >= AppConstants.MAX_SUGGESTIONS) {
            return suggestions.take(AppConstants.MAX_SUGGESTIONS)
        }

        val lower = incomingMessage.lowercase()
        keywordReplies.forEach { (key, reply) ->
            if (lower.contains(key) && suggestions.none { it.text == reply }) {
                suggestions.add(
                    SmartReplySuggestion(text = reply, source = SuggestionSource.KEYWORD)
                )
            }
        }
        if (suggestions.size >= AppConstants.MAX_SUGGESTIONS) {
            analyticsRepository.logReplyGenerated("keyword", 1)
            return suggestions.take(AppConstants.MAX_SUGGESTIONS)
        }

        val settings = settingsRepository.userSettings.first()
        if (settings.geminiApiKey.isBlank()) {
            return suggestions.ifEmpty {
                listOf(
                    SmartReplySuggestion(
                        text = "Thanks for your message! I'll reply soon.",
                        source = SuggestionSource.KEYWORD
                    )
                )
            }
        }

        settingsRepository.resetDailyUsageIfNeeded()
        if (!settingsRepository.incrementAiUsage()) {
            return suggestions + SmartReplySuggestion(
                text = "Daily AI limit reached. Upgrade to Premium or use templates.",
                source = SuggestionSource.KEYWORD
            )
        }

        geminiProvider.apiKey = settings.geminiApiKey
        geminiProvider.model = settings.geminiModel

        val context = messageDao
            .getRecentContext(conversationId, AppConstants.MAX_CONTEXT_MESSAGES)
            .map { "${it.senderName}: ${it.content}" }

        return try {
            val aiReplies = geminiProvider.generateReplies(
                incomingMessage = incomingMessage,
                context = context,
                language = settings.languagePreference,
                customPrompt = settings.customSystemPrompt
            )
            analyticsRepository.logAiCall()
            analyticsRepository.logReplyGenerated("ai", 2)
            val aiSuggestions = aiReplies.map {
                SmartReplySuggestion(text = it, source = SuggestionSource.AI)
            }
            (suggestions + aiSuggestions).distinctBy { it.text }
                .take(AppConstants.MAX_SUGGESTIONS)
        } catch (e: Exception) {
            suggestions.ifEmpty {
                listOf(
                    SmartReplySuggestion(
                        text = "Thanks for your message! I'll get back to you soon.",
                        source = SuggestionSource.KEYWORD
                    )
                )
            }
        }
    }
}
