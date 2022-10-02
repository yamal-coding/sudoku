package com.yamal.sudoku.commons.ui.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

object SudokuDefaultAnimationConstants {
    const val DefaultSlideInVerticalDuration = 1000
}

@Composable
fun SlideInVerticalTransition(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AutomaticAnimatedVisibility(
        modifier = modifier,
        enter =
            slideInVertically(initialOffsetY = { it * 2 }) +
                fadeIn(
                    animationSpec = tween(
                    durationMillis = SudokuDefaultAnimationConstants.DefaultSlideInVerticalDuration
                )),
        content = content
    )
}

@Composable
fun AutomaticAnimatedVisibility(
    modifier: Modifier = Modifier,
    enter: EnterTransition,
    content: @Composable () -> Unit
) {
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visibleState = state,
        enter = enter,
    ) {
        content()
    }
}