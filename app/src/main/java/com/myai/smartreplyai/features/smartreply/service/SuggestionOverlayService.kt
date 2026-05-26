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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
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
import com.myai.smartreplyai.core.components.SuggestionChip
import com.myai.smartreplyai.core.ui.theme.SmartReplyTheme
import com.myai.smartreplyai.features.smartreply.SuggestionStateHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SuggestionOverlayService : Service(), LifecycleOwner, SavedStateRegistryOwner {

    @Inject lateinit var suggestionStateHolder: SuggestionStateHolder

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
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
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
