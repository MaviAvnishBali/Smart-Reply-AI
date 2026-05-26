package com.myai.smartreplyai.domain.repository

import com.myai.smartreplyai.domain.model.AnalyticsSummary

interface AnalyticsRepository {
    suspend fun logReplyGenerated(source: String, timeSavedMinutes: Int)
    suspend fun logCommonQuestion(question: String)
    suspend fun getSummary(): AnalyticsSummary
}
