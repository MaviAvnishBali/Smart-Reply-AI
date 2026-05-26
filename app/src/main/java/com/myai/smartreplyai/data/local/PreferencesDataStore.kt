package com.myai.smartreplyai.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.myai.smartreplyai.core.constants.PreferenceKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("smart_reply_prefs")

@Singleton
class PreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    private object Keys {
        val onboarding = booleanPreferencesKey(PreferenceKeys.ONBOARDING_COMPLETE)
        val darkMode = booleanPreferencesKey(PreferenceKeys.DARK_MODE)
        val overlay = booleanPreferencesKey(PreferenceKeys.OVERLAY_ENABLED)
        val apiKey = stringPreferencesKey(PreferenceKeys.GEMINI_API_KEY)
        val model = stringPreferencesKey(PreferenceKeys.GEMINI_MODEL)
        val prompt = stringPreferencesKey(PreferenceKeys.CUSTOM_SYSTEM_PROMPT)
        val language = stringPreferencesKey(PreferenceKeys.LANGUAGE_PREFERENCE)
        val premium = booleanPreferencesKey(PreferenceKeys.PREMIUM_ACTIVE)
        val dailyUsage = intPreferencesKey(PreferenceKeys.DAILY_AI_USAGE)
        val lastUsageDate = stringPreferencesKey(PreferenceKeys.LAST_USAGE_DATE)
    }

    val onboardingComplete: Flow<Boolean> = dataStore.data.map { it[Keys.onboarding] ?: false }
    val darkMode: Flow<Boolean?> = dataStore.data.map { it[Keys.darkMode] }
    val overlayEnabled: Flow<Boolean> = dataStore.data.map { it[Keys.overlay] ?: false }
    val geminiApiKey: Flow<String> = dataStore.data.map { it[Keys.apiKey] ?: "" }
    val geminiModel: Flow<String> = dataStore.data.map { it[Keys.model] ?: "gemini-2.0-flash" }
    val customPrompt: Flow<String> = dataStore.data.map { it[Keys.prompt] ?: "" }
    val languagePreference: Flow<String> = dataStore.data.map { it[Keys.language] ?: "en" }
    val isPremium: Flow<Boolean> = dataStore.data.map { it[Keys.premium] ?: false }
    val dailyAiUsage: Flow<Int> = dataStore.data.map { it[Keys.dailyUsage] ?: 0 }
    val lastUsageDate: Flow<String> = dataStore.data.map { it[Keys.lastUsageDate] ?: "" }

    suspend fun setOnboardingComplete(value: Boolean) = edit(Keys.onboarding, value)
    suspend fun setDarkMode(value: Boolean?) = dataStore.edit { prefs ->
        if (value == null) prefs.remove(Keys.darkMode) else prefs[Keys.darkMode] = value
    }
    suspend fun setOverlayEnabled(value: Boolean) = edit(Keys.overlay, value)
    suspend fun setGeminiApiKey(value: String) = edit(Keys.apiKey, value)
    suspend fun setGeminiModel(value: String) = edit(Keys.model, value)
    suspend fun setCustomPrompt(value: String) = edit(Keys.prompt, value)
    suspend fun setLanguage(value: String) = edit(Keys.language, value)
    suspend fun setPremium(value: Boolean) = edit(Keys.premium, value)
    suspend fun setDailyAiUsage(value: Int) = edit(Keys.dailyUsage, value)
    suspend fun setLastUsageDate(value: String) = edit(Keys.lastUsageDate, value)

    private suspend fun <T> edit(key: Preferences.Key<T>, value: T) {
        dataStore.edit { it[key] = value }
    }
}
