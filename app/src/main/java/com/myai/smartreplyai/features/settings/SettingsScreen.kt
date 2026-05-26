package com.myai.smartreplyai.features.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myai.smartreplyai.core.components.SmartReplyCard
import com.myai.smartreplyai.core.components.SmartReplyCardRow
import com.myai.smartreplyai.core.ui.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onAiSettings: () -> Unit,
    onPremium: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = Spacing.screenContentPadding,
            verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
        ) {
            item {
                SmartReplyCardRow(
                    title = "AI Settings",
                    subtitle = "API key, model, custom prompt",
                    icon = Icons.Outlined.Psychology,
                    onClick = onAiSettings
                )
            }
            item {
                SmartReplyCardRow(
                    title = "Premium",
                    subtitle = if (state.isPremium) "Active" else "Upgrade for higher limits",
                    icon = Icons.Outlined.Star,
                    onClick = onPremium
                )
            }
            item {
                SmartReplyCard {
                    SettingsToggleRow(
                        title = "Dark Mode",
                        subtitle = "Follows system when off",
                        icon = { Icon(Icons.Outlined.DarkMode, contentDescription = null) },
                        checked = state.darkMode ?: false,
                        onCheckedChange = { viewModel.setDarkMode(it) }
                    )
                }
            }
            item {
                SmartReplyCard {
                    SettingsToggleRow(
                        title = "Overlay Suggestions",
                        subtitle = "Floating quick replies",
                        checked = state.overlayEnabled,
                        onCheckedChange = { viewModel.setOverlay(it) }
                    )
                }
            }
            item {
                SmartReplyCard {
                    Text("Daily AI Usage", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "${state.dailyAiUsage} / ${state.dailyAiLimit} calls today",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            item {
                Text(
                    "Smart Reply AI never auto-sends messages. You always paste manually in WhatsApp.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun SettingsToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    icon: @Composable (() -> Unit)? = null
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            icon?.invoke()
            androidx.compose.foundation.layout.Column {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
