package com.yamal.sudoku.game.viewmodel.ioc

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yamal.sudoku.game.viewmodel.GameFinishedViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

@Composable
fun gameFinishedViewModel(
    gameId: String,
): GameFinishedViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        GameFinishedViewModelFactoryProvider::class.java
    ).gameFinishedViewModelFactory()

    return viewModel(factory = provideFactory(factory, gameId))
}

@AssistedFactory
interface GameFinishedViewModelFactory {
    fun create(
        @Assisted("gameId") gameId: String,
    ): GameFinishedViewModel
}

@EntryPoint
@InstallIn(ActivityComponent::class)
interface GameFinishedViewModelFactoryProvider {
    fun gameFinishedViewModelFactory(): GameFinishedViewModelFactory
}

@Suppress("UNCHECKED_CAST")
private fun provideFactory(
    assistedFactory: GameFinishedViewModelFactory,
    gameId: String,
): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create(gameId) as T
    }
}
