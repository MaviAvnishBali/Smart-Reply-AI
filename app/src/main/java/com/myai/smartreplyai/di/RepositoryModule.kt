package com.myai.smartreplyai.di

import com.myai.smartreplyai.data.repository.AnalyticsRepositoryImpl
import com.myai.smartreplyai.data.repository.ConfigRepositoryImpl
import com.myai.smartreplyai.data.repository.ConversationRepositoryImpl
import com.myai.smartreplyai.data.repository.SettingsRepositoryImpl
import com.myai.smartreplyai.data.repository.TemplateRepositoryImpl
import com.myai.smartreplyai.domain.repository.AnalyticsRepository
import com.myai.smartreplyai.domain.repository.ConfigRepository
import com.myai.smartreplyai.domain.repository.ConversationRepository
import com.myai.smartreplyai.domain.repository.SettingsRepository
import com.myai.smartreplyai.domain.repository.TemplateRepository
import com.myai.smartreplyai.features.ai.provider.AIProvider
import com.myai.smartreplyai.features.ai.provider.GeminiProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindConversationRepository(impl: ConversationRepositoryImpl): ConversationRepository

    @Binds @Singleton
    abstract fun bindTemplateRepository(impl: TemplateRepositoryImpl): TemplateRepository

    @Binds @Singleton
    abstract fun bindAnalyticsRepository(impl: AnalyticsRepositoryImpl): AnalyticsRepository

    @Binds @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds @Singleton
    abstract fun bindConfigRepository(impl: ConfigRepositoryImpl): ConfigRepository

    @Binds @Singleton
    abstract fun bindAiProvider(impl: GeminiProvider): AIProvider
}
