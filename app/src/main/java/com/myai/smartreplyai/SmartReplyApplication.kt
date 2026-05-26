package com.myai.smartreplyai

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.myai.smartreplyai.core.utils.BuildConfigHolder
import com.myai.smartreplyai.R
import com.myai.smartreplyai.features.smartreply.service.SuggestionOverlayService
import com.myai.smartreplyai.data.local.SampleDataSeeder
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class SmartReplyApplication : Application() {

    @Inject lateinit var sampleDataSeeder: SampleDataSeeder

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        BuildConfigHolder.packageName = packageName
        createNotificationChannels()
        MobileAds.initialize(this) {}
        applicationScope.launch {
            sampleDataSeeder.seedIfEmpty()
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                SuggestionOverlayService.CHANNEL_ID,
                getString(R.string.overlay_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = getString(R.string.overlay_channel_desc)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}
