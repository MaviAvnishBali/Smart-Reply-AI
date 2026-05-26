package com.myai.smartreplyai.data.repository

import com.myai.smartreplyai.data.local.dao.AnalyticsDao
import com.myai.smartreplyai.data.local.entity.AnalyticsEntity
import com.myai.smartreplyai.domain.model.AnalyticsSummary
import com.myai.smartreplyai.domain.model.QuestionFrequency
import com.myai.smartreplyai.domain.repository.AnalyticsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsRepositoryImpl @Inject constructor(
    private val analyticsDao: AnalyticsDao
) : AnalyticsRepository {

    override suspend fun logReplyGenerated(source: String, timeSavedMinutes: Int) {
        analyticsDao.insert(
            AnalyticsEntity(
                eventType = "reply_generated",
                eventValue = source,
                metadata = timeSavedMinutes.toString(),
                timestamp = System.currentTimeMillis()
            )
        )
        analyticsDao.insert(
            AnalyticsEntity(
                eventType = "time_saved",
                eventValue = "minutes",
                metadata = timeSavedMinutes.toString(),
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override suspend fun logCommonQuestion(question: String) {
        if (question.isBlank()) return
        analyticsDao.insert(
            AnalyticsEntity(
                eventType = "common_question",
                eventValue = question,
                metadata = "",
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override suspend fun getSummary(): AnalyticsSummary {
        val replies = analyticsDao.countByType("reply_generated")
        val templates = analyticsDao.countByType("template_used")
        val aiCalls = analyticsDao.countByType("ai_call")
        val timeSaved = analyticsDao.totalTimeSavedMinutes()
        val questions = analyticsDao.topQuestions(5).map {
            QuestionFrequency(question = it.eventValue, count = it.cnt)
        }
        return AnalyticsSummary(
            repliesGenerated = replies,
            timeSavedMinutes = timeSaved,
            templatesUsed = templates,
            aiCallsCount = aiCalls,
            commonQuestions = questions
        )
    }

    suspend fun logTemplateUsed() {
        analyticsDao.insert(
            AnalyticsEntity(
                eventType = "template_used",
                eventValue = "template",
                metadata = "",
                timestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun logAiCall() {
        analyticsDao.insert(
            AnalyticsEntity(
                eventType = "ai_call",
                eventValue = "gemini",
                metadata = "",
                timestamp = System.currentTimeMillis()
            )
        )
    }
}
