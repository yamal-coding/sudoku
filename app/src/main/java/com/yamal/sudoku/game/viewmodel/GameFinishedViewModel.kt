package com.yamal.sudoku.game.viewmodel

import androidx.lifecycle.ViewModel
import com.yamal.sudoku.game.status.domain.GetFinishedGameSummary
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class GameFinishedViewModel @AssistedInject constructor(
    @Assisted("gameId") gameId: String,
    getFinishedGameSummary: GetFinishedGameSummary,
) : ViewModel() {

    val state: Flow<GameFinishedViewState> = getFinishedGameSummary(gameId).map { lastFinishedGameSummary ->
        if (lastFinishedGameSummary != null) {
            GameFinishedViewState.Summary
        } else {
            GameFinishedViewState.Idle
        }
    }

    private val _shouldShowNewGameButtons = MutableStateFlow(false)
    val shouldShowNewGameButtons: Flow<Boolean> = _shouldShowNewGameButtons

    fun onPLayAgain() {
        _shouldShowNewGameButtons.value = true
    }
}

sealed class GameFinishedViewState {
    object Idle : GameFinishedViewState()
    object Summary : GameFinishedViewState() // TODO add best time info
}