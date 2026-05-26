package com.myai.smartreplyai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myai.smartreplyai.data.local.dao.AnalyticsDao
import com.myai.smartreplyai.data.local.dao.ConversationDao
import com.myai.smartreplyai.data.local.dao.MessageDao
import com.myai.smartreplyai.data.local.dao.TemplateDao
import com.myai.smartreplyai.data.local.dao.UserSettingsDao
import com.myai.smartreplyai.data.local.entity.AnalyticsEntity
import com.myai.smartreplyai.data.local.entity.ConversationEntity
import com.myai.smartreplyai.data.local.entity.MessageEntity
import com.myai.smartreplyai.data.local.entity.TemplateEntity
import com.myai.smartreplyai.data.local.entity.UserSettingsEntity

@Database(
    entities = [
        ConversationEntity::class,
        MessageEntity::class,
        TemplateEntity::class,
        AnalyticsEntity::class,
        UserSettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SmartReplyDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
    abstract fun messageDao(): MessageDao
    abstract fun templateDao(): TemplateDao
    abstract fun analyticsDao(): AnalyticsDao
    abstract fun userSettingsDao(): UserSettingsDao
}
