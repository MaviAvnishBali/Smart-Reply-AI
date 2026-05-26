package com.myai.smartreplyai.features.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myai.smartreplyai.domain.model.LeadType
import com.myai.smartreplyai.domain.model.Message
import com.myai.smartreplyai.domain.repository.ConversationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ConversationDetailUiState(
    val senderName: String = "",
    val leadType: LeadType = LeadType.NONE,
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class ConversationDetailViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConversationDetailUiState())
    val uiState: StateFlow<ConversationDetailUiState> = _uiState.asStateFlow()

    fun load(conversationId: Long) {
        viewModelScope.launch {
            conversationRepository.markAsRead(conversationId)
            combine(
                conversationRepository.observeConversation(conversationId),
                conversationRepository.observeMessages(conversationId)
            ) { conversation, messages ->
                ConversationDetailUiState(
                    senderName = conversation?.senderName ?: "",
                    leadType = conversation?.leadType ?: LeadType.NONE,
                    messages = messages,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.update { state }
            }
        }
    }
}
