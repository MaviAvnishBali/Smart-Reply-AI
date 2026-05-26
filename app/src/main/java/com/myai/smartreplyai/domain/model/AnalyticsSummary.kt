package com.myai.smartreplyai.domain.model

data class AnalyticsSummary(
    val repliesGenerated: Int,
    val timeSavedMinutes: Int,
    val templatesUsed: Int,
    val aiCallsCount: Int,
    val commonQuestions: List<QuestionFrequency>
)

data class QuestionFrequency(
    val question: String,
    val count: Int
)
