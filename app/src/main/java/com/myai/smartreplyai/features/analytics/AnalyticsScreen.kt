package com.myai.smartreplyai.features.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myai.smartreplyai.core.components.LoadingView
import com.myai.smartreplyai.core.components.SmartReplyCard
import com.myai.smartreplyai.core.ui.Spacing
import com.myai.smartreplyai.core.ui.screenPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    onBack: () -> Unit,
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Analytics") },
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
            else -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .screenPadding()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
            ) {
                StatCard("Replies Generated", state.summary.repliesGenerated.toString())
                StatCard("Time Saved", "${state.summary.timeSavedMinutes} min")
                StatCard("Templates Used", state.summary.templatesUsed.toString())
                StatCard("AI Calls", state.summary.aiCallsCount.toString())
                Text(
                    "Common Questions",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = Spacing.cardGap)
                )
                if (state.summary.commonQuestions.isEmpty()) {
                    Text(
                        "No data yet",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    state.summary.commonQuestions.forEach { q ->
                        SmartReplyCard {
                            Text(q.question, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "×${q.count}",
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(label: String, value: String) {
    SmartReplyCard(modifier = Modifier.fillMaxWidth()) {
        Text(label, style = MaterialTheme.typography.labelLarge)
        Text(
            value,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
