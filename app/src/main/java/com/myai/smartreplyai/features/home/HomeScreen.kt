package com.myai.smartreplyai.features.home

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myai.smartreplyai.core.components.BannerAdView
import com.myai.smartreplyai.core.components.EmptyStateView
import com.myai.smartreplyai.core.components.LeadBadge
import com.myai.smartreplyai.core.components.LoadingView
import com.myai.smartreplyai.core.components.SmartReplyCard
import com.myai.smartreplyai.core.components.SmartReplyCardRow
import com.myai.smartreplyai.core.ui.Spacing
import com.myai.smartreplyai.core.utils.DateTimeUtils
import com.myai.smartreplyai.core.utils.RewardedAdManager
import com.myai.smartreplyai.domain.model.Conversation
import com.myai.smartreplyai.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    onConversationClick: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smart Reply AI") },
                actions = {
                    IconButton(onClick = { onNavigate(Routes.SETTINGS) }) {
                        Icon(Icons.Outlined.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        bottomBar = {
            if (state.isAdsEnabled) {
                BannerAdView(isPremium = state.isPremium)
            }
        }
    ) { padding ->
        when {
            state.isLoading -> LoadingView(Modifier.padding(padding))
            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = Spacing.screenContentPadding,
                verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
            ) {
                // Reward ad trigger card for free users who have used some AI features
                if (!state.isPremium && state.dailyAiUsage > 0 && state.isAdsEnabled) {
                    item {
                        SmartReplyCardRow(
                            title = "Earn Free AI Replies",
                            subtitle = "Daily usage: ${state.dailyAiUsage}/${state.dailyAiLimit}. Watch an ad to get 5 bonus replies!",
                            icon = Icons.Outlined.PlayCircle,
                            onClick = {
                                val activity = context as? Activity
                                if (activity != null) {
                                    RewardedAdManager.showAd(
                                        activity = activity,
                                        onRewardEarned = { viewModel.grantRewardedReplies() },
                                        onAdClosed = {
                                            // Proceed/Reload handled in RewardedAdManager
                                        }
                                    )
                                }
                            }
                        )
                    }
                }

                item {
                    Text("Quick Actions", style = MaterialTheme.typography.titleLarge)
                }
                items(
                    listOf(
                        Triple(Icons.Outlined.Chat, "Conversations", Routes.CONVERSATIONS),
                        Triple(Icons.Outlined.Description, "Templates", Routes.TEMPLATES),
                        Triple(Icons.Outlined.Analytics, "Analytics", Routes.ANALYTICS),
                        Triple(Icons.Outlined.Settings, "Settings", Routes.SETTINGS),
//                        Triple(Icons.Outlined.Star, "Premium", Routes.PREMIUM)
                    )
                ) { (icon, label, route) ->
                    SmartReplyCardRow(
                        title = label,
                        icon = icon,
                        onClick = { onNavigate(route) }
                    )
                }
                item {
                    Text(
                        "Recent Conversations",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = Spacing.cardGap)
                    )
                }
                if (state.conversations.isEmpty()) {
                    item {
                        EmptyStateView(
                            title = "No messages yet",
                            subtitle = "Enable notification access to capture WhatsApp messages.",
                            icon = Icons.Outlined.Chat
                        )
                    }
                } else {
                    items(state.conversations.take(5), key = { it.id }) { conversation ->
                        ConversationRow(
                            conversation = conversation,
                            onClick = { onConversationClick(conversation.id) }
                        )
                    }
                    if (state.conversations.size > 5) {
                        item {
                            SmartReplyCard(onClick = { onNavigate(Routes.CONVERSATIONS) }) {
                                Text(
                                    "View all conversations",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ConversationRow(conversation: Conversation, onClick: () -> Unit) {
    SmartReplyCard(onClick = onClick) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(conversation.senderName, style = MaterialTheme.typography.titleMedium)
                    Text(
                        conversation.lastMessage,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        DateTimeUtils.formatRelative(conversation.lastMessageTime),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (conversation.unreadCount > 0) {
                        Text(
                            "${conversation.unreadCount}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    LeadBadge(
                        leadType = conversation.leadType,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}
