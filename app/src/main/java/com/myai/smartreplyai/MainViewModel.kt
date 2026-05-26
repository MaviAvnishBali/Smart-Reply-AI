package com.myai.smartreplyai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myai.smartreplyai.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    settingsRepository: SettingsRepository
) : ViewModel() {
    val darkTheme = settingsRepository.userSettings
        .map { it.darkMode }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
}
