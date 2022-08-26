package com.yamal.sudoku.commons.json.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun SlideInVerticalTransition(
    content: @Composable () -> Unit
) {
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = slideInVertically(initialOffsetY = { it * 2 }) + fadeIn(animationSpec = tween(durationMillis = 1000))
    ) {
        content()
    }
}