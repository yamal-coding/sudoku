package com.yamal.sudoku.commons.ui.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun DisposableLifecycleAwareEffect(
    key: Any,
    onStart: (() -> Unit)? = null,
    onResume: (() -> Unit)? = null,
    onStop: (() -> Unit)? = null,
    onDispose: (() -> Unit)? = null
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key, lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    onStart?.invoke()
                }
                Lifecycle.Event.ON_RESUME -> {
                    onResume?.invoke()
                }
                Lifecycle.Event.ON_STOP -> {
                    onStop?.invoke()
                }
                else -> {
                    /* Ignoring event */
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            onDispose?.invoke()
        }
    }
}
