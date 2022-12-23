package com.yamal.sudoku.start.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.start.domain.GetExistingGameInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    getExistingGameInfo: GetExistingGameInfo,
) : ViewModel() {

    val startScreenState: Flow<StartScreenState> = getExistingGameInfo().map { existingGameInfo ->
            StartScreenState.Ready(
                existingGame = existingGameInfo?.let {
                    ExistingGameViewData(
                        gameId = it.gameId,
                        difficulty = it.difficulty,
                    )
                }
            )
        }
}

sealed class StartScreenState {
    object Idle : StartScreenState()
    data class Ready(
        val existingGame: ExistingGameViewData?,
    ): StartScreenState()
}

data class ExistingGameViewData(
    val gameId: String,
    val difficulty: Difficulty,
)