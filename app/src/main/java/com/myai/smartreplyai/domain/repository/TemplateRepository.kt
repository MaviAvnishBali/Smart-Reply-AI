package com.myai.smartreplyai.domain.repository

import com.myai.smartreplyai.domain.model.Template
import kotlinx.coroutines.flow.Flow

interface TemplateRepository {
    fun observeTemplates(): Flow<List<Template>>
    suspend fun saveTemplate(template: Template): Long
    suspend fun deleteTemplate(template: Template)
    suspend fun incrementUsage(id: Long)
    suspend fun findMatchingTemplates(message: String): List<Template>
}
