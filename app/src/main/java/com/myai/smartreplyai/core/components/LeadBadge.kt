package com.myai.smartreplyai.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myai.smartreplyai.core.ui.theme.LeadAngry
import com.myai.smartreplyai.core.ui.theme.LeadBooking
import com.myai.smartreplyai.core.ui.theme.LeadPricing
import com.myai.smartreplyai.core.ui.theme.LeadUrgent
import com.myai.smartreplyai.domain.model.LeadType

@Composable
fun LeadBadge(leadType: LeadType, modifier: Modifier = Modifier) {
    val (label, color) = when (leadType) {
        LeadType.PRICING_INQUIRY -> "Pricing" to LeadPricing
        LeadType.URGENT -> "Urgent" to LeadUrgent
        LeadType.BOOKING_INTENT -> "Booking" to LeadBooking
        LeadType.ANGRY_CUSTOMER -> "Angry" to LeadAngry
        LeadType.NONE -> return
    }
    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = Color.White,
        modifier = modifier
            .background(color, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}
