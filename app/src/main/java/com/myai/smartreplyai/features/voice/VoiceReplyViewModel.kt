package com.myai.smartreplyai.features.voice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myai.smartreplyai.domain.model.SmartReplySuggestion
import com.myai.smartreplyai.domain.repository.ConversationRepository
import com.myai.smartreplyai.domain.usecase.GenerateSmartRepliesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VoiceReplyUiState(
    val transcript: String = "",
    val suggestions: List<SmartReplySuggestion> = emptyList(),
    val isListening: Boolean = false,
    val isLoading: Boolean = false
)

@HiltViewModel
class VoiceReplyViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val generateSmartReplies: GenerateSmartRepliesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VoiceReplyUiState())
    val uiState: StateFlow<VoiceReplyUiState> = _uiState.asStateFlow()

    private var conversationId: Long = 0

    fun load(id: Long) {
        conversationId = id
    }

    fun updateTranscript(text: String) {
        _uiState.update { it.copy(transcript = text) }
    }

    fun setListening(listening: Boolean) {
        _uiState.update { it.copy(isListening = listening) }
    }

    fun onSpeechResult(text: String) {
        _uiState.update { it.copy(transcript = text) }
        generateFromTranscript()
    }

    fun generateFromTranscript() {
        viewModelScope.launch {
            val transcript = _uiState.value.transcript
            if (transcript.isBlank()) return@launch
            _uiState.update { it.copy(isLoading = true) }
            try {
                val contextMessage = if (conversationId > 0) {
                    val messages = conversationRepository.observeMessages(conversationId).first()
                    messages.lastOrNull { it.isIncoming }?.content ?: transcript
                } else transcript
                val combined = "User voice intent: $transcript\nContext message: $contextMessage"
                val suggestions = generateSmartReplies(conversationId, combined)
                _uiState.update {
                    it.copy(suggestions = suggestions, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
