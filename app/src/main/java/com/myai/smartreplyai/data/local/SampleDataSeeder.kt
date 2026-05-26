package com.myai.smartreplyai.data.local

import com.myai.smartreplyai.data.local.dao.ConversationDao
import com.myai.smartreplyai.data.local.dao.TemplateDao
import com.myai.smartreplyai.data.local.dao.UserSettingsDao
import com.myai.smartreplyai.data.local.entity.TemplateEntity
import com.myai.smartreplyai.data.local.entity.UserSettingsEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleDataSeeder @Inject constructor(
    private val conversationDao: ConversationDao,
    private val templateDao: TemplateDao,
    private val userSettingsDao: UserSettingsDao
) {
    suspend fun seedIfEmpty() {
        if (userSettingsDao.get() == null) {
            userSettingsDao.insert(UserSettingsEntity())
        }
        if (conversationDao.count() > 0) return
        val now = System.currentTimeMillis()
        val templates = listOf(
            TemplateEntity(
                title = "Thanks",
                content = "Thank you for reaching out! I'll get back to you shortly.",
                category = "General",
                keywords = "thanks,thank you,dhanyavad",
                usageCount = 0,
                createdAt = now
            ),
            TemplateEntity(
                title = "Pricing",
                content = "Our pricing starts at ₹999. Would you like a detailed quote?",
                category = "Sales",
                keywords = "price,cost,rate,kitna,pricing,quote",
                usageCount = 0,
                createdAt = now
            ),
            TemplateEntity(
                title = "Booking",
                content = "Sure! Please share your preferred date and time for booking.",
                category = "Booking",
                keywords = "book,appointment,slot,schedule",
                usageCount = 0,
                createdAt = now
            ),
            TemplateEntity(
                title = "Hindi Greeting",
                content = "Namaste! Main aapki madad karne ke liye yahan hoon. Batayein kaise help kar sakta hoon?",
                category = "Hindi",
                keywords = "namaste,hello,hi",
                usageCount = 0,
                createdAt = now
            ),
            TemplateEntity(
                title = "Away",
                content = "I'm currently away. I'll reply as soon as possible. Thanks for your patience!",
                category = "General",
                keywords = "busy,away,later",
                usageCount = 0,
                createdAt = now
            )
        )
        templates.forEach { templateDao.insert(it) }
    }
}
