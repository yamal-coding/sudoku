package com.yamal.sudoku.game.viewmodel

import androidx.lifecycle.ViewModel
import com.yamal.sudoku.commons.ui.TimeCounterFormatter
import com.yamal.sudoku.game.status.domain.GetFinishedGameSummary
import com.yamal.sudoku.game.status.domain.LastFinishedGameSummary
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

@HiltViewModel(assistedFactory = GameFinishedViewModel.Factory::class)
class GameFinishedViewModel @AssistedInject constructor(
    @Assisted(GAME_ID) gameId: String,
    getFinishedGameSummary: GetFinishedGameSummary,
    private val timeCounterFormatter: TimeCounterFormatter,
) : ViewModel() {

    val state: Flow<GameFinishedViewState> = getFinishedGameSummary(gameId).map { lastFinishedGameSummary ->
        lastFinishedGameSummary?.toViewData() ?: GameFinishedViewState.Idle
    }

    private val _shouldShowNewGameButtons = MutableStateFlow(false)
    val shouldShowNewGameButtons: Flow<Boolean> = _shouldShowNewGameButtons

    fun onPLayAgain() {
        _shouldShowNewGameButtons.value = true
    }

    private fun LastFinishedGameSummary.toViewData(): GameFinishedViewState.Summary =
        GameFinishedViewState.Summary(
            isNewBestTime = isNewBestTime,
            gameTime = gameTimeInSeconds?.let { timeCounterFormatter.format(it) },
        )

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(GAME_ID) gameId: String): GameFinishedViewModel
    }

    private companion object {
        const val GAME_ID = "gameId"
    }
}

sealed class GameFinishedViewState {
    data object Idle : GameFinishedViewState()
    data class Summary(
        val isNewBestTime: Boolean,
        val gameTime: String?,
    ) : GameFinishedViewState()
}
