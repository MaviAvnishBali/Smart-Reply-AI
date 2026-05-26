package com.myai.smartreplyai.features.splash

import androidx.lifecycle.ViewModel
import com.myai.smartreplyai.BuildConfig
import com.myai.smartreplyai.domain.repository.ConfigRepository
import com.myai.smartreplyai.domain.repository.SettingsRepository
import com.myai.smartreplyai.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    suspend fun resolveStartRoute(): String {
        val onboardingDone = settingsRepository.onboardingComplete.first()
        return if (!onboardingDone) Routes.ONBOARDING else Routes.HOME
    }
}
