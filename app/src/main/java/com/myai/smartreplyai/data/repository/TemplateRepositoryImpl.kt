package com.myai.smartreplyai.data.repository

import com.myai.smartreplyai.data.local.dao.TemplateDao
import com.myai.smartreplyai.data.mapper.TemplateMapper
import com.myai.smartreplyai.domain.model.Template
import com.myai.smartreplyai.domain.repository.TemplateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemplateRepositoryImpl @Inject constructor(
    private val templateDao: TemplateDao
) : TemplateRepository {

    override fun observeTemplates(): Flow<List<Template>> =
        templateDao.observeAll().map { list -> list.map(TemplateMapper::toDomain) }

    override suspend fun saveTemplate(template: Template): Long =
        templateDao.insert(TemplateMapper.toEntity(template))

    override suspend fun deleteTemplate(template: Template) {
        templateDao.delete(TemplateMapper.toEntity(template))
    }

    override suspend fun incrementUsage(id: Long) {
        templateDao.incrementUsage(id)
    }

    override suspend fun findMatchingTemplates(message: String): List<Template> {
        val lower = message.lowercase()
        return templateDao.observeAll().first()
            .map(TemplateMapper::toDomain)
            .filter { template ->
                template.keywords.any { keyword ->
                    lower.contains(keyword.lowercase())
                }
            }
            .sortedByDescending { it.usageCount }
            .take(3)
    }
}
