package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.level.data.LevelsRepository
import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.Difficulty
import javax.inject.Inject

open class LoadNewBoard @Inject constructor(
    private val gameStatusRepository: GameStatusRepository,
    private val levelsRepository: LevelsRepository,
    private val currentGame: CurrentGame,
    private val gameFactory: GameFactory,
) {
    open suspend operator fun invoke(difficulty: Difficulty) {
        currentGame.onGameStarted {
            val newLevel = levelsRepository.getNewLevel(difficulty)

            if (newLevel != null) {
                levelsRepository.markLevelAsAlreadyReturned(newLevel.id)
                startNewGame(newBoard = newLevel.board)
            } else {
                currentGame.onNewBoardNotFound()
            }
        }
    }

    private fun startNewGame(newBoard: Board) {
        val newGame = gameFactory.get(newBoard)
        currentGame.onGameReady(newGame)
        gameStatusRepository.saveBoard(newBoard)
    }
}
