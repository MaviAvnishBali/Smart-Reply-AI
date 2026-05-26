package com.myai.smartreplyai.features.smartreply

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myai.smartreplyai.domain.model.ReplyTone
import com.myai.smartreplyai.domain.model.SmartReplySuggestion
import com.myai.smartreplyai.domain.repository.ConversationRepository
import com.myai.smartreplyai.domain.usecase.GenerateSmartRepliesUseCase
import com.myai.smartreplyai.domain.usecase.RewriteToneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SmartReplyUiState(
    val incomingMessage: String = "",
    val suggestions: List<SmartReplySuggestion> = emptyList(),
    val draftReply: String = "",
    val selectedTone: ReplyTone = ReplyTone.FRIENDLY,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class SmartReplyViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val generateSmartReplies: GenerateSmartRepliesUseCase,
    private val rewriteTone: RewriteToneUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SmartReplyUiState())
    val uiState: StateFlow<SmartReplyUiState> = _uiState.asStateFlow()

    private var conversationId: Long = 0

    fun load(id: Long) {
        conversationId = id
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val conversation = conversationRepository.observeConversation(id).first()
                val messages = conversationRepository.observeMessages(id).first()
                val incoming = messages.lastOrNull { it.isIncoming }?.content
                    ?: conversation?.lastMessage
                    ?: ""
                val suggestions = generateSmartReplies(id, incoming)
                _uiState.update {
                    it.copy(
                        incomingMessage = incoming,
                        suggestions = suggestions,
                        draftReply = suggestions.firstOrNull()?.text ?: "",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Failed to generate replies")
                }
            }
        }
    }

    fun updateDraft(text: String) {
        _uiState.update { it.copy(draftReply = text) }
    }

    fun selectTone(tone: ReplyTone) {
        _uiState.update { it.copy(selectedTone = tone) }
    }

    fun rewriteDraft(onResult: (String) -> Unit) {
        viewModelScope.launch {
            val draft = _uiState.value.draftReply
            if (draft.isBlank()) return@launch
            try {
                val rewritten = rewriteTone(draft, _uiState.value.selectedTone)
                _uiState.update { it.copy(draftReply = rewritten) }
                onResult(rewritten)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}
