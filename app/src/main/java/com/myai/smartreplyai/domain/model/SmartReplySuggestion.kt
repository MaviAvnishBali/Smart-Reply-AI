package com.myai.smartreplyai.domain.model

data class SmartReplySuggestion(
    val text: String,
    val source: SuggestionSource,
    val tone: ReplyTone? = null
)

enum class SuggestionSource {
    TEMPLATE,
    KEYWORD,
    AI
}
