package com.myai.smartreplyai.data.repository

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.myai.smartreplyai.domain.model.AppConfig
import com.myai.smartreplyai.domain.repository.ConfigRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigRepositoryImpl @Inject constructor() : ConfigRepository {

    private val fallbackConfig = AppConfig(
        forceUpdateEnabled = false,
        flexibleUpdateEnabled = true,
        minimumSupportedVersionCode = 1,
        latestVersionCode = 5,
        isAdsEnabled = true,
        maintenanceMode = false,
        maintenanceMessage = "App is under maintenance. Please try again later.",
        baseUrl = "https://api.example.com/"
    )

    init {
        try {
            val remoteConfig = FirebaseRemoteConfig.getInstance()
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0 // Fetch immediately for testing/development
            }
            remoteConfig.setConfigSettingsAsync(configSettings)

            val defaults = mapOf(
                "force_update_enabled" to false,
                "flexible_update_enabled" to true,
                "minimum_supported_version_code" to 1L,
                "latest_version_code" to 5L,
                "is_ads_enabled" to true,
                "maintenance_mode" to false,
                "maintenance_message" to "App is under maintenance. Please try again later.",
                "base_url" to "https://api.example.com/"
            )
            remoteConfig.setDefaultsAsync(defaults)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAppConfig(): AppConfig {
        return try {
            val remoteConfig = FirebaseRemoteConfig.getInstance()
            try {
                remoteConfig.fetchAndActivate().await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            
            AppConfig(
                forceUpdateEnabled = remoteConfig.getBoolean("force_update_enabled"),
                flexibleUpdateEnabled = remoteConfig.getBoolean("flexible_update_enabled"),
                minimumSupportedVersionCode = remoteConfig.getLong("minimum_supported_version_code").toInt().coerceAtLeast(1),
                latestVersionCode = remoteConfig.getLong("latest_version_code").toInt().coerceAtLeast(1),
                isAdsEnabled = remoteConfig.getBoolean("is_ads_enabled"),
                maintenanceMode = remoteConfig.getBoolean("maintenance_mode"),
                maintenanceMessage = remoteConfig.getString("maintenance_message").ifBlank { fallbackConfig.maintenanceMessage },
                baseUrl = remoteConfig.getString("base_url").ifBlank { fallbackConfig.baseUrl }
            )
        } catch (e: Exception) {
            fallbackConfig
        }
    }
}

private suspend fun <T> com.google.android.gms.tasks.Task<T>.await(): T =
    suspendCancellableCoroutine { continuation ->
        addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(task.result)
            } else {
                continuation.resumeWithException(task.exception ?: RuntimeException("Task failed"))
            }
        }
    }
