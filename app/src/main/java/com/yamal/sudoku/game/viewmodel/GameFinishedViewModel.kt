package com.yamal.sudoku.game.viewmodel

import androidx.lifecycle.ViewModel
import com.yamal.sudoku.commons.ui.TimeCounterFormatter
import com.yamal.sudoku.game.status.domain.GetFinishedGameSummary
import com.yamal.sudoku.game.status.domain.LastFinishedGameSummary
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class GameFinishedViewModel @AssistedInject constructor(
    @Assisted("gameId") gameId: String,
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
}

sealed class GameFinishedViewState {
    object Idle : GameFinishedViewState()
    data class Summary(
        val isNewBestTime: Boolean,
        val gameTime: String?,
    ) : GameFinishedViewState()
}
