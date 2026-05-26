package com.myai.smartreplyai.navigation

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val PERMISSIONS = "permissions"
    const val HOME = "home"
    const val CONVERSATIONS = "conversations"
    const val CONVERSATION_DETAIL = "conversation/{conversationId}"
    const val SMART_REPLY = "smart_reply/{conversationId}"
    const val TEMPLATES = "templates"
    const val ANALYTICS = "analytics"
    const val SETTINGS = "settings"
    const val AI_SETTINGS = "ai_settings"
    const val PREMIUM = "premium"
    const val VOICE_REPLY = "voice_reply/{conversationId}"
    fun conversationDetail(id: Long) = "conversation/$id"
    fun smartReply(id: Long) = "smart_reply/$id"
    fun voiceReply(id: Long) = "voice_reply/$id"
}
