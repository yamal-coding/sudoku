package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import javax.inject.Inject

open class LoadSavedBoard @Inject constructor(
    private val repository: GameStatusRepository,
    private val currentGame: CurrentGame,
    private val gameFactory: GameFactory,
    private val timeCounter: TimeCounter,
) {
    open suspend operator fun invoke() {
        currentGame.onGameStarted {
            currentGame.onLoadingGame()
            val savedBoard = repository.getSavedBoardSync()

            if (savedBoard != null) {
                val savedGame = gameFactory.get(savedBoard)
                // If no current time was found, we shouldn't count the remaining time since it would be incorrect
                repository.getTimeCounterSync()?.let { currentTimeCount ->
                    timeCounter.start(initialSeconds = currentTimeCount)
                }
                currentGame.onGameReady(savedGame)
            } else {
                currentGame.onSavedGameNotFound()
            }
        }
    }

    private companion object {
        const val DEFAULT_INITIAL_TIME_COUNTER = 0L
    }
}
