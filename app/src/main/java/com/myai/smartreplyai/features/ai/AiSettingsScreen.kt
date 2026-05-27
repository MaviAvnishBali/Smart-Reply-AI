package com.myai.smartreplyai.features.ai

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myai.smartreplyai.core.ui.Spacing
import com.myai.smartreplyai.core.ui.screenPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiSettingsScreen(
    onBack: () -> Unit,
    viewModel: AiSettingsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Settings") },
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
            OutlinedTextField(
                value = state.apiKey,
                onValueChange = viewModel::updateApiKey,
                label = { Text("Gemini API Key") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )
            OutlinedTextField(
                value = state.model,
                onValueChange = viewModel::updateModel,
                label = { Text("Model") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("gemini-2.0-flash") }
            )
            OutlinedTextField(
                value = state.customPrompt,
                onValueChange = viewModel::updatePrompt,
                label = { Text("Custom System Prompt") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4
            )
            OutlinedTextField(
                value = state.language,
                onValueChange = viewModel::updateLanguage,
                label = { Text("Language (en / hi / hinglish)") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { viewModel.save(onBack) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }

            Spacer(Modifier.height(20.dp))
            Text(
                text = "How to create Gemini API Key:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "1. Open Google AI Studio\n" +
                        "2. Sign in with your Google account\n" +
                        "3. Click 'Create API Key'\n" +
                        "4. Copy the generated key\n" +
                        "5. Paste it above and save",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Open Google AI Studio",
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://aistudio.google.com/app/apikey")
                        )
                        context.startActivity(intent)
                    }
            )
        }
    }
}
