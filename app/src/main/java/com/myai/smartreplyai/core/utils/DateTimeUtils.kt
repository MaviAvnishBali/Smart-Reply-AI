package com.myai.smartreplyai.core.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateTimeUtils {
    private val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
    private val fullFormat = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault())

    fun formatMessageTime(timestamp: Long): String {
        val now = Calendar.getInstance()
        val messageCal = Calendar.getInstance().apply { timeInMillis = timestamp }
        return when {
            isSameDay(now, messageCal) -> timeFormat.format(Date(timestamp))
            isYesterday(now, messageCal) -> "Yesterday"
            now.get(Calendar.YEAR) == messageCal.get(Calendar.YEAR) ->
                dateFormat.format(Date(timestamp))
            else -> fullFormat.format(Date(timestamp))
        }
    }

    fun formatRelative(timestamp: Long): String {
        val diff = System.currentTimeMillis() - timestamp
        return when {
            diff < TimeUnit.MINUTES.toMillis(1) -> "Just now"
            diff < TimeUnit.HOURS.toMillis(1) ->
                "${TimeUnit.MILLISECONDS.toMinutes(diff)}m ago"
            diff < TimeUnit.DAYS.toMillis(1) ->
                "${TimeUnit.MILLISECONDS.toHours(diff)}h ago"
            else -> dateFormat.format(Date(timestamp))
        }
    }

    fun todayKey(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())

    private fun isSameDay(a: Calendar, b: Calendar): Boolean =
        a.get(Calendar.YEAR) == b.get(Calendar.YEAR) &&
            a.get(Calendar.DAY_OF_YEAR) == b.get(Calendar.DAY_OF_YEAR)

    private fun isYesterday(now: Calendar, message: Calendar): Boolean {
        val yesterday = now.clone() as Calendar
        yesterday.add(Calendar.DAY_OF_YEAR, -1)
        return yesterday.get(Calendar.YEAR) == message.get(Calendar.YEAR) &&
            yesterday.get(Calendar.DAY_OF_YEAR) == message.get(Calendar.DAY_OF_YEAR)
    }
}
