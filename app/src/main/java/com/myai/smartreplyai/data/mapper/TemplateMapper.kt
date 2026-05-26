package com.myai.smartreplyai.data.mapper

import com.myai.smartreplyai.data.local.entity.TemplateEntity
import com.myai.smartreplyai.domain.model.Template

object TemplateMapper {
    fun toDomain(entity: TemplateEntity): Template = Template(
        id = entity.id,
        title = entity.title,
        content = entity.content,
        category = entity.category,
        keywords = entity.keywords.split(",").map { it.trim() }.filter { it.isNotEmpty() },
        usageCount = entity.usageCount,
        createdAt = entity.createdAt
    )

    fun toEntity(domain: Template): TemplateEntity = TemplateEntity(
        id = domain.id,
        title = domain.title,
        content = domain.content,
        category = domain.category,
        keywords = domain.keywords.joinToString(","),
        usageCount = domain.usageCount,
        createdAt = domain.createdAt
    )
}
