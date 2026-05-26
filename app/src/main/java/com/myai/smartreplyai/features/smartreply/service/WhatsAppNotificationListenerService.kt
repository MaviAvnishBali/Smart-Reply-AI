package com.myai.smartreplyai.features.smartreply.service

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.myai.smartreplyai.data.local.PreferencesDataStore
import com.myai.smartreplyai.domain.repository.ConversationRepository
import com.myai.smartreplyai.domain.usecase.GenerateSmartRepliesUseCase
import com.myai.smartreplyai.features.smartreply.SuggestionStateHolder
import com.myai.smartreplyai.features.smartreply.parser.WhatsAppNotificationParser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WhatsAppNotificationListenerService : NotificationListenerService() {

    @Inject lateinit var parser: WhatsAppNotificationParser
    @Inject lateinit var conversationRepository: ConversationRepository
    @Inject lateinit var generateSmartReplies: GenerateSmartRepliesUseCase
    @Inject lateinit var suggestionStateHolder: SuggestionStateHolder
    @Inject lateinit var preferences: PreferencesDataStore

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        serviceScope.launch { handleNotification(sbn, removed = false) }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        // No auto-actions on removal — Play Store safe
    }

    private suspend fun handleNotification(sbn: StatusBarNotification, removed: Boolean) {
        val parsed = parser.parse(sbn) ?: return
        val (conversationId, _) = conversationRepository.saveIncomingMessage(
            senderName = parsed.senderName,
            senderKey = parsed.senderKey,
            content = parsed.message,
            isGroup = parsed.isGroup,
            packageName = parsed.packageName,
            notificationKey = parsed.notificationKey,
            timestamp = parsed.timestamp,
            isEdited = parsed.isEdited
        )

        val suggestions = generateSmartReplies(conversationId, parsed.message)
        suggestionStateHolder.show(
            conversationId = conversationId,
            senderName = parsed.senderName,
            message = parsed.message,
            suggestions = suggestions
        )

        val overlayEnabled = preferences.overlayEnabled.first()
        if (overlayEnabled) {
            val intent = Intent(this, SuggestionOverlayService::class.java).apply {
                action = SuggestionOverlayService.ACTION_SHOW
            }
            startForegroundService(intent)
        }
    }
}
