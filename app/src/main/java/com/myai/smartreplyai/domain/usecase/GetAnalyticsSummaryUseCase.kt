package com.myai.smartreplyai.domain.usecase

import com.myai.smartreplyai.domain.model.AnalyticsSummary
import com.myai.smartreplyai.domain.repository.AnalyticsRepository
import javax.inject.Inject

class GetAnalyticsSummaryUseCase @Inject constructor(
    private val repository: AnalyticsRepository
) {
    suspend operator fun invoke(): AnalyticsSummary = repository.getSummary()
}
