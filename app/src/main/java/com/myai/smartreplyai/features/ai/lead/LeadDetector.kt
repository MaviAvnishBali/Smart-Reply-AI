package com.myai.smartreplyai.features.ai.lead

import com.myai.smartreplyai.domain.model.LeadType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LeadDetector @Inject constructor() {

    private val pricingKeywords = listOf(
        "price", "cost", "rate", "kitna", "pricing", "quote", "charges", "fees", "amount"
    )
    private val urgentKeywords = listOf(
        "urgent", "asap", "immediately", "jaldi", "emergency", "now", "today"
    )
    private val bookingKeywords = listOf(
        "book", "booking", "appointment", "slot", "schedule", "reserve", "table"
    )
    private val angryKeywords = listOf(
        "angry", "worst", "refund", "complaint", "fraud", "cheat", "useless", "pathetic"
    )

    fun detect(message: String): LeadType {
        val lower = message.lowercase()
        return when {
            angryKeywords.any { lower.contains(it) } -> LeadType.ANGRY_CUSTOMER
            urgentKeywords.any { lower.contains(it) } -> LeadType.URGENT
            pricingKeywords.any { lower.contains(it) } -> LeadType.PRICING_INQUIRY
            bookingKeywords.any { lower.contains(it) } -> LeadType.BOOKING_INTENT
            else -> LeadType.NONE
        }
    }
}
