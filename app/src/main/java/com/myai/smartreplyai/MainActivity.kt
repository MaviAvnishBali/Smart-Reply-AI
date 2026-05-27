package com.myai.smartreplyai

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.myai.smartreplyai.core.ui.theme.SmartReplyTheme
import com.myai.smartreplyai.domain.repository.ConfigRepository
import com.myai.smartreplyai.navigation.SmartReplyNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var configRepository: ConfigRepository

    private lateinit var appUpdateManager: AppUpdateManager
    private val updateRequestCode = 1001

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        
        checkForUpdates()
        
        enableEdgeToEdge()
        setContent {
            val darkModePref by mainViewModel.darkTheme.collectAsStateWithLifecycle()
            val darkTheme = darkModePref ?: isSystemInDarkTheme()
            SmartReplyTheme(darkTheme = darkTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SmartReplyNavHost()
                }
            }
        }
    }

    private fun checkForUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
        
        lifecycleScope.launch {
            try {
                val config = configRepository.getAppConfig()
                val appUpdateInfoTask = appUpdateManager.appUpdateInfo
                
                appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                    val isUpdateAvailable = appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    if (isUpdateAvailable) {
                        if (BuildConfig.VERSION_CODE < config.minimumSupportedVersionCode && config.forceUpdateEnabled) {
                            if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                                appUpdateManager.startUpdateFlowForResult(
                                    appUpdateInfo,
                                    AppUpdateType.IMMEDIATE,
                                    this@MainActivity,
                                    updateRequestCode
                                )
                            }
                        } else if (BuildConfig.VERSION_CODE < config.latestVersionCode && config.flexibleUpdateEnabled) {
                            if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                                appUpdateManager.registerListener(installStateUpdatedListener)
                                appUpdateManager.startUpdateFlowForResult(
                                    appUpdateInfo,
                                    AppUpdateType.FLEXIBLE,
                                    this@MainActivity,
                                    updateRequestCode
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            Toast.makeText(this, "Update downloaded. Restarting app...", Toast.LENGTH_LONG).show()
            appUpdateManager.completeUpdate()
        }
    }

    override fun onResume() {
        super.onResume()
        if (::appUpdateManager.isInitialized) {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        updateRequestCode
                    )
                }
            }
        }
    }
}
