package com.myai.smartreplyai.features.smartreply

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myai.smartreplyai.core.components.ErrorView
import com.myai.smartreplyai.core.components.LoadingView
import com.myai.smartreplyai.core.components.SmartReplyCard
import com.myai.smartreplyai.core.components.SuggestionChip
import com.myai.smartreplyai.core.ui.Spacing
import com.myai.smartreplyai.domain.model.ReplyTone
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartReplyScreen(
    conversationId: Long,
    onBack: () -> Unit,
    viewModel: SmartReplyViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(conversationId) {
        viewModel.load(conversationId)
    }

    fun copyText(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("reply", text))
        scope.launch { snackbar.showSnackbar("Copied to clipboard — paste in WhatsApp") }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smart Reply") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        when {
            state.isLoading -> LoadingView(Modifier.padding(padding), "Generating suggestions...")
            state.error != null -> ErrorView(
                message = state.error!!,
                onRetry = { viewModel.load(conversationId) },
                modifier = Modifier.padding(padding)
            )
            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = Spacing.screenContentPadding,
                verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
            ) {
                item {
                    SmartReplyCard {
                        Text(
                            "Incoming message",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            state.incomingMessage,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
                item {
                    Text("Suggestions", style = MaterialTheme.typography.titleMedium)
                }
                items(state.suggestions) { suggestion ->
                    SuggestionChip(
                        text = "${suggestion.text} (${suggestion.source.name})",
                        onClick = { copyText(suggestion.text) }
                    )
                }
                item {
                    Text("Tone Rewriter", style = MaterialTheme.typography.titleMedium)
                }
                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(ReplyTone.entries) { tone ->
                            FilterChip(
                                selected = state.selectedTone == tone,
                                onClick = { viewModel.selectTone(tone) },
                                label = { Text(tone.displayName) }
                            )
                        }
                    }
                }
                item {
                    OutlinedTextField(
                        value = state.draftReply,
                        onValueChange = viewModel::updateDraft,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Your reply draft") },
                        minLines = 3
                    )
                }
                item {
                    FilterChip(
                        selected = false,
                        onClick = { viewModel.rewriteDraft { copyText(it) } },
                        label = { Text("Rewrite with ${state.selectedTone.displayName}") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
