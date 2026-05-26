package com.myai.smartreplyai.features.permissions

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Accessibility
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myai.smartreplyai.core.components.SmartReplyCard
import com.myai.smartreplyai.core.ui.Spacing
import com.myai.smartreplyai.core.ui.screenPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionsScreen(
    onContinue: () -> Unit,
    viewModel: PermissionsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val micLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { viewModel.refresh(context) }

    val notifLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { viewModel.refresh(context) }

    LaunchedEffect(Unit) {
        viewModel.refresh(context)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Permissions") }) }
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
                text = "Grant permissions for the best experience. You stay in control — no auto-send.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            PermissionCard(
                icon = Icons.Outlined.NotificationsActive,
                title = "Notification Access",
                subtitle = "Read WhatsApp notifications locally",
                granted = state.notificationListenerEnabled,
                onRequest = { viewModel.openNotificationSettings(context) }
            )

            PermissionCard(
                icon = Icons.Outlined.Layers,
                title = "Display Over Apps",
                subtitle = "Show floating reply suggestions",
                granted = state.overlayGranted,
                onRequest = { viewModel.openOverlaySettings(context) }
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                PermissionCard(
                    icon = Icons.Outlined.NotificationsActive,
                    title = "Post Notifications",
                    subtitle = "Overlay service status notification",
                    granted = state.postNotificationsGranted,
                    onRequest = { notifLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) }
                )
            }

            PermissionCard(
                icon = Icons.Outlined.Mic,
                title = "Microphone",
                subtitle = "Voice-to-text reply flow",
                granted = state.micGranted,
                onRequest = { micLauncher.launch(Manifest.permission.RECORD_AUDIO) }
            )

            PermissionCard(
                icon = Icons.Outlined.Accessibility,
                title = "Accessibility (Optional)",
                subtitle = "Placeholder for future — not required for MVP",
                granted = false,
                onRequest = { viewModel.openAccessibilitySettings(context) }
            )

            SmartReplyCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Enable overlay suggestions", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "Show floating quick replies",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = state.overlayEnabledPref,
                        onCheckedChange = { viewModel.setOverlayPref(it) }
                    )
                }
            }

            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continue to Dashboard")
            }
        }
    }
}

@Composable
private fun PermissionCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    granted: Boolean,
    onRequest: () -> Unit
) {
    SmartReplyCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
            Button(onClick = onRequest, enabled = !granted) {
                Text(if (granted) "Granted" else "Enable")
            }
        }
    }
}
