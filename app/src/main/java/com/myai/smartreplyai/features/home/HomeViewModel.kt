package com.myai.smartreplyai.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myai.smartreplyai.BuildConfig
import com.myai.smartreplyai.domain.model.AppConfig
import com.myai.smartreplyai.domain.model.Conversation
import com.myai.smartreplyai.domain.repository.ConfigRepository
import com.myai.smartreplyai.domain.repository.SettingsRepository
import com.myai.smartreplyai.domain.usecase.ObserveConversationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val conversations: List<Conversation> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isPremium: Boolean = false,
    val dailyAiUsage: Int = 0,
    val dailyAiLimit: Int = 0,
    val isAdsEnabled: Boolean = true
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    observeConversations: ObserveConversationsUseCase,
    private val settingsRepository: SettingsRepository,
    private val configRepository: ConfigRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.userSettings.collect { settings ->
                _uiState.update {
                    it.copy(
                        isPremium = settings.isPremium,
                        dailyAiUsage = settings.dailyAiUsage,
                        dailyAiLimit = settings.dailyAiLimit
                    )
                }
            }
        }

        viewModelScope.launch {
            observeConversations()
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { conversations ->
                    _uiState.update {
                        it.copy(
                            conversations = conversations,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }

        viewModelScope.launch {
            try {
                val config = configRepository.getAppConfig()
                _uiState.update { it.copy(isAdsEnabled = config.isAdsEnabled) }
            } catch (e: Exception) {
                // Ignore config fetch errors
            }
        }
    }



    fun grantRewardedReplies() {
        viewModelScope.launch {
            settingsRepository.grantRewardedBonus(5)
        }
    }
}
