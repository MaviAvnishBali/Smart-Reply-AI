package com.myai.smartreplyai.features.voice

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myai.smartreplyai.core.components.LoadingView
import com.myai.smartreplyai.core.components.SuggestionChip
import com.myai.smartreplyai.core.ui.Spacing
import com.myai.smartreplyai.core.ui.screenPadding
import com.myai.smartreplyai.core.utils.PermissionUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceReplyScreen(
    conversationId: Long,
    onBack: () -> Unit,
    viewModel: VoiceReplyViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(conversationId) {
        viewModel.load(conversationId)
    }

    val speechRecognizer = remember {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            SpeechRecognizer.createSpeechRecognizer(context)
        } else null
    }

    DisposableEffect(speechRecognizer) {
        onDispose { speechRecognizer?.destroy() }
    }

    fun startListening() {
        if (!PermissionUtils.hasRecordAudioPermission(context)) return
        val recognizer = speechRecognizer ?: return
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }
        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                viewModel.setListening(true)
            }
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                viewModel.setListening(false)
            }
            override fun onError(error: Int) {
                viewModel.setListening(false)
            }
            override fun onResults(results: Bundle?) {
                val text = results
                    ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.firstOrNull()
                if (!text.isNullOrBlank()) {
                    viewModel.onSpeechResult(text)
                }
            }
            override fun onPartialResults(partialResults: Bundle?) {
                val text = partialResults
                    ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.firstOrNull()
                if (!text.isNullOrBlank()) viewModel.updateTranscript(text)
            }
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
        recognizer.startListening(intent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Voice Reply") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .screenPadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
        ) {
            Text(
                "Speak your intent — we'll generate reply suggestions. Copy and paste into WhatsApp manually.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            OutlinedTextField(
                value = state.transcript,
                onValueChange = viewModel::updateTranscript,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Transcript") },
                minLines = 3
            )
            Button(
                onClick = { startListening() },
                modifier = Modifier.fillMaxWidth(),
                enabled = PermissionUtils.hasRecordAudioPermission(context) && !state.isListening
            ) {
                Icon(Icons.Outlined.Mic, contentDescription = null)
                Text(
                    if (state.isListening) " Listening..." else " Start Voice Input",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Button(
                onClick = { viewModel.generateFromTranscript() },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.transcript.isNotBlank() && !state.isLoading
            ) {
                Text("Generate AI Replies")
            }
            if (state.isLoading) {
                LoadingView(message = "Generating...")
            }
            state.suggestions.forEach { suggestion ->
                SuggestionChip(
                    text = suggestion.text,
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("reply", suggestion.text))
                    }
                )
            }
        }
    }
}
