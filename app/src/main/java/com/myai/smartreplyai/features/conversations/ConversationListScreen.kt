package com.myai.smartreplyai.features.conversations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myai.smartreplyai.core.components.EmptyStateView
import com.myai.smartreplyai.core.components.LeadBadge
import com.myai.smartreplyai.core.components.LoadingView
import com.myai.smartreplyai.core.components.SmartReplyCard
import com.myai.smartreplyai.core.ui.Spacing
import com.myai.smartreplyai.core.utils.DateTimeUtils
import com.myai.smartreplyai.domain.model.Conversation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationListScreen(
    onBack: () -> Unit,
    onConversationClick: (Long) -> Unit,
    viewModel: ConversationsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Conversations") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when {
            state.isLoading -> LoadingView(Modifier.padding(padding))
            state.conversations.isEmpty() -> EmptyStateView(
                title = "No conversations",
                subtitle = "WhatsApp messages will appear here after notification access is enabled.",
                icon = Icons.Outlined.Chat,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(Spacing.screenContentPadding)
            )
            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = Spacing.screenContentPadding,
                verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
            ) {
                items(state.conversations, key = { it.id }) { conversation ->
                    SmartReplyCard(onClick = { onConversationClick(conversation.id) }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    conversation.senderName,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    conversation.lastMessage,
                                    maxLines = 2,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                LeadBadge(leadType = conversation.leadType)
                                Text(
                                    DateTimeUtils.formatRelative(conversation.lastMessageTime),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
