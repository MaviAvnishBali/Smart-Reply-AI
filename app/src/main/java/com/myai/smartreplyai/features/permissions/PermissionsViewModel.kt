package com.myai.smartreplyai.features.permissions

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myai.smartreplyai.core.utils.PermissionUtils
import com.myai.smartreplyai.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PermissionsUiState(
    val notificationListenerEnabled: Boolean = false,
    val overlayGranted: Boolean = false,
    val micGranted: Boolean = false,
    val postNotificationsGranted: Boolean = true,
    val overlayEnabledPref: Boolean = false
)

@HiltViewModel
class PermissionsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PermissionsUiState())
    val uiState: StateFlow<PermissionsUiState> = _uiState.asStateFlow()

    fun refresh(context: Context) {
        viewModelScope.launch {
            val overlayPref = settingsRepository.userSettings.first().overlayEnabled
            _uiState.value = PermissionsUiState(
                notificationListenerEnabled = PermissionUtils.isNotificationListenerEnabled(context),
                overlayGranted = PermissionUtils.isOverlayGranted(context),
                micGranted = PermissionUtils.hasRecordAudioPermission(context),
                postNotificationsGranted = PermissionUtils.hasPostNotificationsPermission(context),
                overlayEnabledPref = overlayPref
            )
        }
    }

    fun openNotificationSettings(context: Context) {
        context.startActivity(PermissionUtils.notificationListenerSettingsIntent().apply {
            addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    fun openOverlaySettings(context: Context) {
        context.startActivity(PermissionUtils.overlaySettingsIntent().apply {
            addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    fun openAccessibilitySettings(context: Context) {
        context.startActivity(PermissionUtils.accessibilitySettingsIntent().apply {
            addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    fun setOverlayPref(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setOverlayEnabled(enabled)
            _uiState.value = _uiState.value.copy(overlayEnabledPref = enabled)
        }
    }
}
