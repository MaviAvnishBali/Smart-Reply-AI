package com.myai.smartreplyai.domain.model

data class AppConfig(
    val forceUpdateEnabled: Boolean,
    val flexibleUpdateEnabled: Boolean,
    val minimumSupportedVersionCode: Int,
    val latestVersionCode: Int,
    val isAdsEnabled: Boolean,
    val maintenanceMode: Boolean,
    val maintenanceMessage: String,
    val baseUrl: String,
    // UI fields for updates not in JSON, but useful to keep with defaults
    val updateUrl: String = "https://play.google.com/store/apps/details?id=com.myai.smartreplyai",
    val updateTitle: String = "Update Smart Reply AI",
    val updateMessage: String = "A new version is available. Please update."
)
