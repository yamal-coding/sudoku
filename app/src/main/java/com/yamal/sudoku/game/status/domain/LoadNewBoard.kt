package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.level.data.LevelsRepository
import com.yamal.sudoku.game.level.domain.Level
import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.stats.domain.IncreaseGamesPlayed
import javax.inject.Inject

open class LoadNewBoard @Inject constructor(
    private val gameStatusRepository: GameStatusRepository,
    private val levelsRepository: LevelsRepository,
    private val currentGame: CurrentGame,
    private val gameFactory: GameFactory,
    private val timeCounter: TimeCounter,
    private val increaseGamesPlayed: IncreaseGamesPlayed,
) {
    open suspend operator fun invoke(difficulty: Difficulty) {
        val newLevel = levelsRepository.getNewLevel(difficulty)

        if (newLevel != null) {
            startNewGame(newLevel)
        } else {
            tryAgainAfterResettingAlreadyReturnedLevels(difficulty)
        }
    }

    private suspend fun startNewGame(level: Level) {
        levelsRepository.markLevelAsAlreadyReturned(level.id)

        val newGame = gameFactory.get(level.board)
        timeCounter.start(initialSeconds = INITIAL_TIME_COUNTER)
        currentGame.onGameReady(newGame)
        gameStatusRepository.saveBoard(level.board)

        increaseGamesPlayed(level.difficulty)
    }

    private suspend fun tryAgainAfterResettingAlreadyReturnedLevels(difficulty: Difficulty) {
        levelsRepository.resetAlreadyReturnedLevels(difficulty)

        val newLevel = levelsRepository.getNewLevel(difficulty)

        if (newLevel != null) {
            startNewGame(newLevel)
        } else {
            currentGame.onNewBoardNotFound()
        }
    }

    private companion object {
        const val INITIAL_TIME_COUNTER = 0L
    }
}
