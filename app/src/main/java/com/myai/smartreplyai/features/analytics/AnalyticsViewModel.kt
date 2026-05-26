package com.myai.smartreplyai.features.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myai.smartreplyai.domain.model.AnalyticsSummary
import com.myai.smartreplyai.domain.usecase.GetAnalyticsSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AnalyticsUiState(
    val summary: AnalyticsSummary = AnalyticsSummary(0, 0, 0, 0, emptyList()),
    val isLoading: Boolean = true
)

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val getAnalyticsSummary: GetAnalyticsSummaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnalyticsUiState())
    val uiState: StateFlow<AnalyticsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val summary = getAnalyticsSummary()
            _uiState.update { it.copy(summary = summary, isLoading = false) }
        }
    }
}
