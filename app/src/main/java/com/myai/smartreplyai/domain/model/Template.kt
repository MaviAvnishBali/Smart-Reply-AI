package com.myai.smartreplyai.domain.model

data class Template(
    val id: Long,
    val title: String,
    val content: String,
    val category: String,
    val keywords: List<String>,
    val usageCount: Int,
    val createdAt: Long
)
