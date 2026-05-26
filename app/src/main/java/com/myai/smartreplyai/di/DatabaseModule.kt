package com.myai.smartreplyai.di

import android.content.Context
import androidx.room.Room
import com.myai.smartreplyai.data.local.SmartReplyDatabase
import com.myai.smartreplyai.data.local.dao.AnalyticsDao
import com.myai.smartreplyai.data.local.dao.ConversationDao
import com.myai.smartreplyai.data.local.dao.MessageDao
import com.myai.smartreplyai.data.local.dao.TemplateDao
import com.myai.smartreplyai.data.local.dao.UserSettingsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SmartReplyDatabase =
        Room.databaseBuilder(
            context,
            SmartReplyDatabase::class.java,
            "smart_reply_db"
        ).build()

    @Provides fun provideConversationDao(db: SmartReplyDatabase): ConversationDao = db.conversationDao()
    @Provides fun provideMessageDao(db: SmartReplyDatabase): MessageDao = db.messageDao()
    @Provides fun provideTemplateDao(db: SmartReplyDatabase): TemplateDao = db.templateDao()
    @Provides fun provideAnalyticsDao(db: SmartReplyDatabase): AnalyticsDao = db.analyticsDao()
    @Provides fun provideUserSettingsDao(db: SmartReplyDatabase): UserSettingsDao = db.userSettingsDao()
}
