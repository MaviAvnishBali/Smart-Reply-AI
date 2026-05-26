package com.myai.smartreplyai.features.settings

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

data class SettingsUiState(
    val darkMode: Boolean? = null,
    val overlayEnabled: Boolean = false,
    val isPremium: Boolean = false,
    val dailyAiUsage: Int = 0,
    val dailyAiLimit: Int = 15
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.userSettings.collect { settings ->
                _uiState.update {
                    SettingsUiState(
                        darkMode = settings.darkMode,
                        overlayEnabled = settings.overlayEnabled,
                        isPremium = settings.isPremium,
                        dailyAiUsage = settings.dailyAiUsage,
                        dailyAiLimit = settings.dailyAiLimit
                    )
                }
            }
        }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setDarkMode(enabled) }
    }

    fun setOverlay(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setOverlayEnabled(enabled) }
    }
}
