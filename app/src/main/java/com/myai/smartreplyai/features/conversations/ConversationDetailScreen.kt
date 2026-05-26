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
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myai.smartreplyai.core.components.LeadBadge
import com.myai.smartreplyai.core.components.LoadingView
import com.myai.smartreplyai.core.ui.Spacing
import com.myai.smartreplyai.core.utils.DateTimeUtils
import com.myai.smartreplyai.domain.model.Message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationDetailScreen(
    conversationId: Long,
    onBack: () -> Unit,
    onSmartReply: () -> Unit,
    onVoiceReply: () -> Unit,
    viewModel: ConversationDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(conversationId) {
        viewModel.load(conversationId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(state.senderName.ifBlank { "Conversation" })
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.screenHorizontal, vertical = Spacing.cardGap),
                horizontalArrangement = Arrangement.spacedBy(Spacing.cardGap)
            ) {
                Button(
                    onClick = onSmartReply,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Outlined.AutoAwesome, contentDescription = null)
                    Text(" Smart Reply", modifier = Modifier.padding(start = 4.dp))
                }
                OutlinedButton(onClick = onVoiceReply) {
                    Icon(Icons.Outlined.Mic, contentDescription = null)
                }
            }
        }
    ) { padding ->
        when {
            state.isLoading -> LoadingView(Modifier.padding(padding))
            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = Spacing.screenContentPadding,
                verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
            ) {
                item {
                    LeadBadge(leadType = state.leadType)
                }
                items(state.messages, key = { it.id }) { message ->
                    MessageBubble(message)
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(message: Message) {
    val alignment = if (message.isIncoming) Alignment.Start else Alignment.End
    val containerColor = if (message.isIncoming) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.primaryContainer
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isIncoming) Arrangement.Start else Arrangement.End
    ) {
        androidx.compose.material3.Surface(
            color = containerColor,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.End
        ) {
                if (message.isEdited) {
                    Text(
                        "Edited",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Start
                    )
                }
                Text(
                    message.content,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = if (message.isIncoming) {
                        androidx.compose.ui.text.style.TextAlign.Start
                    } else {
                        androidx.compose.ui.text.style.TextAlign.End
                    }
                )
                Text(
                    DateTimeUtils.formatMessageTime(message.timestamp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
