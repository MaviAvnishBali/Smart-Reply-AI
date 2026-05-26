package com.myai.smartreplyai.features.smartreply

import com.myai.smartreplyai.domain.model.SmartReplySuggestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

data class OverlaySuggestionState(
    val conversationId: Long = 0,
    val senderName: String = "",
    val incomingMessage: String = "",
    val suggestions: List<SmartReplySuggestion> = emptyList(),
    val visible: Boolean = false
)

@Singleton
class SuggestionStateHolder @Inject constructor() {
    private val _state = MutableStateFlow(OverlaySuggestionState())
    val state: StateFlow<OverlaySuggestionState> = _state.asStateFlow()

    fun show(
        conversationId: Long,
        senderName: String,
        message: String,
        suggestions: List<SmartReplySuggestion>
    ) {
        _state.value = OverlaySuggestionState(
            conversationId = conversationId,
            senderName = senderName,
            incomingMessage = message,
            suggestions = suggestions,
            visible = true
        )
    }

    fun hide() {
        _state.value = _state.value.copy(visible = false)
    }
}
