package com.myai.smartreplyai.domain.model

data class PremiumEntitlement(
    val isPremium: Boolean,
    val dailyAiLimit: Int,
    val dailyAiUsed: Int,
    val canUseOverlay: Boolean,
    val canUseToneRewrite: Boolean,
    val canUseVoiceReply: Boolean,
    val canUseLeadDetection: Boolean
) {
    val hasAiQuotaRemaining: Boolean get() = dailyAiUsed < dailyAiLimit
    val remainingAiCalls: Int get() = (dailyAiLimit - dailyAiUsed).coerceAtLeast(0)
}
