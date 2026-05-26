package com.myai.smartreplyai.domain.usecase

import com.myai.smartreplyai.domain.model.Conversation
import com.myai.smartreplyai.domain.repository.ConversationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveConversationsUseCase @Inject constructor(
    private val repository: ConversationRepository
) {
    operator fun invoke(): Flow<List<Conversation>> = repository.observeConversations()
}
