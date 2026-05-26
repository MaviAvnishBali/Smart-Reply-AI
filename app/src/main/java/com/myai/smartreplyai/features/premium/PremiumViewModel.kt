package com.myai.smartreplyai.features.premium

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myai.smartreplyai.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PremiumUiState(
    val isPremium: Boolean = false,
    val dailyAiLimit: Int = 15,
    val remainingAiCalls: Int = 15
)

@HiltViewModel
class PremiumViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PremiumUiState())
    val uiState: StateFlow<PremiumUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.premiumEntitlement.collect { entitlement ->
                _uiState.update {
                    PremiumUiState(
                        isPremium = entitlement.isPremium,
                        dailyAiLimit = entitlement.dailyAiLimit,
                        remainingAiCalls = entitlement.remainingAiCalls
                    )
                }
            }
        }
    }

    fun togglePremiumDemo() {
        viewModelScope.launch {
            settingsRepository.setPremiumActive(true)
        }
    }
}
