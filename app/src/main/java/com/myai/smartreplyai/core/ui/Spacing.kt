package com.myai.smartreplyai.core.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object Spacing {
    val screenHorizontal = 16.dp
    val screenVertical = 16.dp
    val cardGap = 12.dp
    val cardInner = 16.dp
    val listItemInner = 12.dp

    val screenContentPadding = PaddingValues(
        horizontal = screenHorizontal,
        vertical = screenVertical
    )
}

fun Modifier.screenPadding(): Modifier = padding(
    horizontal = Spacing.screenHorizontal,
    vertical = Spacing.screenVertical
)
