package com.myai.smartreplyai.features.ai.provider

import com.myai.smartreplyai.domain.model.ReplyTone

interface AIProvider {
    suspend fun generateReplies(
        incomingMessage: String,
        context: List<String>,
        language: String,
        customPrompt: String
    ): List<String>

    suspend fun rewriteTone(text: String, tone: ReplyTone, language: String): String
}
