package com.myai.smartreplyai.features.premium

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
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
import com.myai.smartreplyai.core.components.SmartReplyCard
import com.myai.smartreplyai.core.ui.Spacing
import com.myai.smartreplyai.core.ui.screenPadding
import com.myai.smartreplyai.core.ui.theme.AccentGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumScreen(
    onBack: () -> Unit,
    viewModel: PremiumViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Premium") },
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
            SmartReplyCard {
                Text(
                    "Smart Reply Premium",
                    style = MaterialTheme.typography.headlineMedium,
                    color = AccentGold
                )
                Text(
                    "Subscription architecture ready — payments not integrated in MVP.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            SmartReplyCard {
                val features = listOf(
                    "500 AI replies per day",
                    "Floating overlay suggestions",
                    "Priority tone rewriting",
                    "Advanced lead insights"
                )
                features.forEachIndexed { index, feature ->
                    androidx.compose.foundation.layout.Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = if (index == 0) 0.dp else 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.Check, contentDescription = null)
                        Text(feature, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            SmartReplyCard {
                Text(
                    "Remaining today: ${state.remainingAiCalls} / ${state.dailyAiLimit}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Button(
                onClick = { viewModel.togglePremiumDemo() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isPremium
            ) {
                Text(if (state.isPremium) "Premium Active (Demo)" else "Enable Premium (Demo)")
            }
            if (state.isPremium) {
                Text(
                    "Demo mode enabled locally. Real billing via Play Billing can be added later.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
