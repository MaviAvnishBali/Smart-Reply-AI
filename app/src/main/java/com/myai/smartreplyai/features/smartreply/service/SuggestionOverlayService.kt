package com.myai.smartreplyai.features.smartreply.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.myai.smartreplyai.MainActivity
import com.myai.smartreplyai.R
import com.myai.smartreplyai.core.ui.theme.SmartReplyTheme
import com.myai.smartreplyai.features.smartreply.SuggestionStateHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SuggestionOverlayService : Service(), LifecycleOwner, SavedStateRegistryOwner {

    @Inject
    lateinit var suggestionStateHolder: SuggestionStateHolder

    private val lifecycleRegistry = LifecycleRegistry(this)
    private val savedStateController = SavedStateRegistryController.create(this)

    private var windowManager: WindowManager? = null
    private var overlayView: ComposeView? = null

    override val lifecycle: Lifecycle get() = lifecycleRegistry
    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateController.savedStateRegistry

    override fun onCreate() {
        super.onCreate()
        savedStateController.performRestore(null)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        startForeground(NOTIFICATION_ID, buildForegroundNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_SHOW -> showOverlay()
            ACTION_HIDE -> hideOverlay()
        }
        return START_STICKY
    }

    private fun showOverlay() {

        if (overlayView != null) return

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.BOTTOM
            y = 100
        }

        val composeView = ComposeView(this).apply {

            setViewTreeLifecycleOwner(this@SuggestionOverlayService)
            setViewTreeSavedStateRegistryOwner(this@SuggestionOverlayService)

            setContent {

                SmartReplyTheme {

                    val state by suggestionStateHolder.state.collectAsState()

                    if (!state.visible) return@SmartReplyTheme

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp, vertical = 12.dp
                            )
                    ) {

                        // Main Glass Card
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(34.dp))
                                // Frosted Glass Blur
                                .blur(0.5.dp)
                                // Glass Background
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = 0.14f),
                                            Color.White.copy(alpha = 0.06f),
                                            Color.White.copy(alpha = 0.03f)
                                        )
                                    )
                                )
                                // Glass Border
                                .border(
                                    width = 1.dp, brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = 0.35f),
                                            Color.White.copy(alpha = 0.08f)
                                        )
                                    ), shape = RoundedCornerShape(34.dp)
                                )
                        ) {
                            // Shine Effect
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.White.copy(alpha = 0.12f),
                                                Color.Transparent,
                                                Color.Transparent
                                            )
                                        )
                                    )
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                // Header
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "Reply to ${state.senderName}",
                                            color = Color.White,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.SemiBold
                                        )

                                        Spacer(modifier = Modifier.height(6.dp))

                                        Text(
                                            text = state.incomingMessage,
                                            color = Color.White.copy(alpha = 0.72f),
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 2
                                        )
                                    }

                                    // Dismiss Button
                                    Box(
                                        modifier = Modifier
                                            .size(38.dp)
                                            .clip(CircleShape)
                                            .background(
                                                Color.White.copy(alpha = 0.10f)
                                            )
                                            .border(
                                                1.dp, Color.White.copy(alpha = 0.15f), CircleShape
                                            )
                                            .clickable {

                                                suggestionStateHolder.hide()
                                                hideOverlay()
                                            }, contentAlignment = Alignment.Center
                                    ) {

                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                // Suggestion Chips
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {

                                    state.suggestions.forEach { suggestion ->

                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(22.dp))

                                                // Glass chip
                                                .background(
                                                    Brush.linearGradient(
                                                        colors = listOf(
                                                            Color.White.copy(alpha = 0.10f),
                                                            Color.White.copy(alpha = 0.04f)
                                                        )
                                                    )
                                                )

                                                .border(
                                                    1.dp,
                                                    Color.White.copy(alpha = 0.10f),
                                                    RoundedCornerShape(22.dp)
                                                )

                                                .clickable {

                                                    copyToClipboard(suggestion.text)

                                                    suggestionStateHolder.hide()
                                                    hideOverlay()
                                                }) {

                                            // top glow
                                            Box(
                                                modifier = Modifier
                                                    .matchParentSize()
                                                    .background(
                                                        Brush.verticalGradient(
                                                            colors = listOf(
                                                                Color.White.copy(alpha = 0.05f),
                                                                Color.Transparent
                                                            )
                                                        )
                                                    )
                                            )

                                            Text(
                                                text = suggestion.text,
                                                color = Color.White,
                                                style = MaterialTheme.typography.bodyMedium,
                                                modifier = Modifier.padding(
                                                    horizontal = 16.dp, vertical = 14.dp
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        overlayView = composeView

        windowManager?.addView(composeView, params)

        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }/*
        private fun showOverlay() {
            if (overlayView != null) return
            windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
            ).apply {
                gravity = Gravity.BOTTOM
                y = 120
            }

            val composeView = ComposeView(this).apply {
                setViewTreeLifecycleOwner(this@SuggestionOverlayService)
                setViewTreeSavedStateRegistryOwner(this@SuggestionOverlayService)
                setContent {
                    SmartReplyTheme {
                        val state by suggestionStateHolder.state.collectAsState()
                        if (!state.visible) return@SmartReplyTheme
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = com.myai.smartreplyai.core.ui.Spacing.screenHorizontal,
                                    vertical = com.myai.smartreplyai.core.ui.Spacing.cardGap
                                ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Reply to ${state.senderName}",
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Text(
                                    text = state.incomingMessage,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 2
                                )
                                state.suggestions.forEach { suggestion ->
                                    SuggestionChip(
                                        text = suggestion.text,
                                        onClick = {
                                            copyToClipboard(suggestion.text)
                                            suggestionStateHolder.hide()
                                            hideOverlay()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            overlayView = composeView
            windowManager?.addView(composeView, params)
            lifecycleRegistry.currentState = Lifecycle.State.STARTED
        }
    */

    private fun hideOverlay() {
        overlayView?.let { windowManager?.removeView(it) }
        overlayView = null
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(
            android.content.ClipData.newPlainText("smart_reply", text)
        )
    }

    private fun buildForegroundNotification(): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.overlay_notification_title))
            .setContentText(getString(R.string.overlay_notification_text))
            .setSmallIcon(R.drawable.app_icon).setContentIntent(pendingIntent).setOngoing(true)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        hideOverlay()
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        super.onDestroy()
    }

    companion object {
        const val ACTION_SHOW = "com.myai.smartreplyai.SHOW_OVERLAY"
        const val ACTION_HIDE = "com.myai.smartreplyai.HIDE_OVERLAY"
        const val CHANNEL_ID = "overlay_channel"
        const val NOTIFICATION_ID = 1001
    }
}
