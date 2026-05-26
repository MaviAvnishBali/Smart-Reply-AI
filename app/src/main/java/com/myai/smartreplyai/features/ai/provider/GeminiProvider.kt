package com.myai.smartreplyai.features.ai.provider

import com.myai.smartreplyai.core.constants.AppConstants
import com.myai.smartreplyai.domain.model.ReplyTone
import com.myai.smartreplyai.features.ai.data.GeminiApiService
import com.myai.smartreplyai.features.ai.data.GeminiContent
import com.myai.smartreplyai.features.ai.data.GeminiPart
import com.myai.smartreplyai.features.ai.data.GeminiRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiProvider @Inject constructor(
    private val api: GeminiApiService
) : AIProvider {

    var apiKey: String = ""
    var model: String = AppConstants.DEFAULT_GEMINI_MODEL

    override suspend fun generateReplies(
        incomingMessage: String,
        context: List<String>,
        language: String,
        customPrompt: String
    ): List<String> {
        if (apiKey.isBlank()) throw IllegalStateException("Gemini API key not configured")
        val system = buildString {
            append(customPrompt.ifBlank { DEFAULT_REPLY_PROMPT })
            append("\nLanguage preference: ")
            append(language)
            append("\nReturn exactly 3 short reply suggestions, one per line, no numbering.")
        }
        val contextBlock = if (context.isNotEmpty()) {
            "Recent context:\n${context.joinToString("\n")}\n\n"
        } else ""
        val prompt = "$system\n\n$contextBlock Incoming: $incomingMessage"
        val text = callGemini(prompt)
        return parseSuggestions(text)
    }

    override suspend fun rewriteTone(text: String, tone: ReplyTone, language: String): String {
        if (apiKey.isBlank()) throw IllegalStateException("Gemini API key not configured")
        val prompt = """
            Rewrite this WhatsApp reply in ${tone.displayName} tone.
            Language: $language. Keep it concise and natural.
            Reply only with the rewritten text, no quotes.
            
            Original: $text
        """.trimIndent()
        return callGemini(prompt).trim()
    }

    private suspend fun callGemini(prompt: String): String {
        val response = api.generateContent(
            model = model,
            apiKey = apiKey,
            request = GeminiRequest(
                contents = listOf(
                    GeminiContent(parts = listOf(GeminiPart(text = prompt)))
                )
            )
        )
        return response.candidates
            ?.firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text
            ?.trim()
            ?: throw IllegalStateException("Empty AI response")
    }

    private fun parseSuggestions(text: String): List<String> =
        text.lines()
            .map { it.trim().removePrefix("-").removePrefix("•").trim() }
            .filter { it.isNotBlank() }
            .take(AppConstants.MAX_SUGGESTIONS)
            .ifEmpty { listOf(text.trim()) }

    companion object {
        val DEFAULT_REPLY_PROMPT = """
            You are a helpful WhatsApp smart reply assistant for small businesses in India.
            Generate polite, context-aware reply suggestions. Support English, Hindi, and Hinglish.
            Never auto-send. User copies manually. Be concise.
        """.trimIndent()
    }
}
