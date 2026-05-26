package com.myai.smartreplyai.domain.repository

import com.myai.smartreplyai.domain.model.AppConfig

interface ConfigRepository {
    suspend fun getAppConfig(): AppConfig
}
