package com.myai.smartreplyai.features.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myai.smartreplyai.core.constants.AppConstants
import com.myai.smartreplyai.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AiSettingsUiState(
    val apiKey: String = "",
    val model: String = AppConstants.DEFAULT_GEMINI_MODEL,
    val customPrompt: String = "",
    val language: String = "en"
)

@HiltViewModel
class AiSettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiSettingsUiState())
    val uiState: StateFlow<AiSettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val settings = settingsRepository.userSettings.first()
            _uiState.update {
                AiSettingsUiState(
                    apiKey = settings.geminiApiKey,
                    model = settings.geminiModel,
                    customPrompt = settings.customSystemPrompt,
                    language = settings.languagePreference
                )
            }
        }
    }

    fun updateApiKey(value: String) = _uiState.update { it.copy(apiKey = value) }
    fun updateModel(value: String) = _uiState.update { it.copy(model = value) }
    fun updatePrompt(value: String) = _uiState.update { it.copy(customPrompt = value) }
    fun updateLanguage(value: String) = _uiState.update { it.copy(language = value) }

    fun save(onDone: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            settingsRepository.saveGeminiApiKey(state.apiKey.trim())
            settingsRepository.saveGeminiModel(state.model.ifBlank { AppConstants.DEFAULT_GEMINI_MODEL })
            settingsRepository.saveCustomPrompt(state.customPrompt)
            settingsRepository.setLanguage(state.language)
            onDone()
        }
    }
}
