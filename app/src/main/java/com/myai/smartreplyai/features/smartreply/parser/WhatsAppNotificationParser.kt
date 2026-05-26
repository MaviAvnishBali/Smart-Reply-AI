package com.myai.smartreplyai.features.smartreply.parser

import android.app.Notification
import android.os.Bundle
import android.service.notification.StatusBarNotification
import com.myai.smartreplyai.core.constants.AppConstants
import com.myai.smartreplyai.core.extensions.normalizeSender
import javax.inject.Inject
import javax.inject.Singleton

data class ParsedWhatsAppNotification(
    val senderName: String,
    val senderKey: String,
    val message: String,
    val isGroup: Boolean,
    val packageName: String,
    val notificationKey: String,
    val timestamp: Long,
    val isEdited: Boolean
)

@Singleton
class WhatsAppNotificationParser @Inject constructor() {

    private val recentHashes = LinkedHashMap<String, Long>()

    fun parse(sbn: StatusBarNotification): ParsedWhatsAppNotification? {
        val pkg = sbn.packageName
        if (pkg != AppConstants.WHATSAPP_PACKAGE &&
            pkg != AppConstants.WHATSAPP_BUSINESS_PACKAGE
        ) {
            return null
        }

        val extras = sbn.notification.extras ?: return null
        val title = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString()?.trim()
            ?: return null
        val text = extractMessageText(extras) ?: return null
        if (text.isBlank() || isSystemMessage(text)) return null

        val isGroup = title.contains(":")
        val senderName = if (isGroup) {
            title.substringBefore(":").trim()
        } else {
            title.normalizeSender()
        }
        val groupSender = if (isGroup) {
            title.substringAfter(":", title).trim()
        } else {
            senderName
        }

        val senderKey = "${pkg}_${groupSender.lowercase()}"
        val notificationKey = "${sbn.key}_${sbn.postTime}_${text.hashCode()}"
        val dedupeKey = "${senderKey}_${text.hashCode()}"
        val now = System.currentTimeMillis()
        val lastSeen = recentHashes[dedupeKey]
        if (lastSeen != null && now - lastSeen < AppConstants.DUPLICATE_WINDOW_MS) {
            return null
        }
        recentHashes[dedupeKey] = now
        if (recentHashes.size > 200) {
            val oldest = recentHashes.entries.minByOrNull { it.value }?.key
            if (oldest != null) recentHashes.remove(oldest)
        }

        val isEdited = text.startsWith("Edited:") ||
            extras.getCharSequence("android.text")?.toString()?.contains("edited", true) == true

        return ParsedWhatsAppNotification(
            senderName = if (isGroup) groupSender else senderName,
            senderKey = senderKey,
            message = text.removePrefix("Edited:").trim(),
            isGroup = isGroup,
            packageName = pkg,
            notificationKey = notificationKey,
            timestamp = sbn.postTime,
            isEdited = isEdited
        )
    }

    private fun extractMessageText(extras: Bundle): String? {
        val lines = extras.getCharSequenceArray(Notification.EXTRA_TEXT_LINES)
        if (lines != null && lines.isNotEmpty()) {
            return lines.lastOrNull()?.toString()?.trim()
        }
        val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString()
        if (!bigText.isNullOrBlank()) return bigText.trim()
        return extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()?.trim()
    }

    private fun isSystemMessage(text: String): Boolean {
        val lower = text.lowercase()
        return lower.contains("checking for new messages") ||
            lower.contains("whatsapp web") ||
            lower.contains("backup")
    }
}
