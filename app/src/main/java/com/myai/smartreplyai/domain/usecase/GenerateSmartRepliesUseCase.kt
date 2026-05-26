package com.myai.smartreplyai.domain.usecase

import com.myai.smartreplyai.domain.model.SmartReplySuggestion
import com.myai.smartreplyai.features.ai.engine.SmartReplyEngine
import javax.inject.Inject

class GenerateSmartRepliesUseCase @Inject constructor(
    private val engine: SmartReplyEngine
) {
    suspend operator fun invoke(
        conversationId: Long,
        incomingMessage: String
    ): List<SmartReplySuggestion> =
        engine.generateSuggestions(conversationId, incomingMessage)
}
