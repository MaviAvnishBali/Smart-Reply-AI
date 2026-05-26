package com.myai.smartreplyai.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.myai.smartreplyai.features.ai.AiSettingsScreen
import com.myai.smartreplyai.features.analytics.AnalyticsScreen
import com.myai.smartreplyai.features.conversations.ConversationDetailScreen
import com.myai.smartreplyai.features.conversations.ConversationListScreen
import com.myai.smartreplyai.features.home.HomeScreen
import com.myai.smartreplyai.features.onboarding.OnboardingScreen
import com.myai.smartreplyai.features.permissions.PermissionsScreen
import com.myai.smartreplyai.features.premium.PremiumScreen
import com.myai.smartreplyai.features.settings.SettingsScreen
import com.myai.smartreplyai.features.smartreply.SmartReplyScreen
import com.myai.smartreplyai.features.splash.SplashScreen
import com.myai.smartreplyai.features.templates.TemplatesScreen
import com.myai.smartreplyai.features.voice.VoiceReplyScreen

@Composable
fun SmartReplyNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onComplete = {
                    navController.navigate(Routes.PERMISSIONS) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.PERMISSIONS) {
            PermissionsScreen(
                onContinue = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.PERMISSIONS) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.HOME) {
            HomeScreen(
                onNavigate = { route -> navController.navigate(route) },
                onConversationClick = { id ->
                    navController.navigate(Routes.conversationDetail(id))
                }
            )
        }
        composable(Routes.CONVERSATIONS) {
            ConversationListScreen(
                onBack = { navController.popBackStack() },
                onConversationClick = { id ->
                    navController.navigate(Routes.conversationDetail(id))
                }
            )
        }
        composable(
            route = Routes.CONVERSATION_DETAIL,
            arguments = listOf(navArgument("conversationId") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("conversationId") ?: 0L
            ConversationDetailScreen(
                conversationId = id,
                onBack = { navController.popBackStack() },
                onSmartReply = { navController.navigate(Routes.smartReply(id)) },
                onVoiceReply = { navController.navigate(Routes.voiceReply(id)) }
            )
        }
        composable(
            route = Routes.SMART_REPLY,
            arguments = listOf(navArgument("conversationId") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("conversationId") ?: 0L
            SmartReplyScreen(
                conversationId = id,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.TEMPLATES) {
            TemplatesScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.ANALYTICS) {
            AnalyticsScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onAiSettings = { navController.navigate(Routes.AI_SETTINGS) },
                onPremium = { navController.navigate(Routes.PREMIUM) }
            )
        }
        composable(Routes.AI_SETTINGS) {
            AiSettingsScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.PREMIUM) {
            PremiumScreen(onBack = { navController.popBackStack() })
        }
        composable(
            route = Routes.VOICE_REPLY,
            arguments = listOf(navArgument("conversationId") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("conversationId") ?: 0L
            VoiceReplyScreen(
                conversationId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
