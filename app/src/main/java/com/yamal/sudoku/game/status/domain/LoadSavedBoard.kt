package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import javax.inject.Inject

open class LoadSavedBoard @Inject constructor(
    private val repository: GameStatusRepository,
    private val currentGame: CurrentGame,
    private val gameFactory: GameFactory,
) {
    open suspend operator fun invoke() {
        currentGame.onGameStarted {
            currentGame.onLoadingGame()
            val savedBoard = repository.getSavedBoardSync()

            if (savedBoard != null) {
                val savedGame = gameFactory.get(savedBoard)
                currentGame.onGameReady(savedGame)
            } else {
                currentGame.onSavedGameNotFound()
            }
        }
    }
}
