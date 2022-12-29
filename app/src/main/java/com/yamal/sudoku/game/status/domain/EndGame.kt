package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.commons.thread.ApplicationScope
import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.stats.domain.IsNewBestTime
import com.yamal.sudoku.stats.domain.UpdateGameFinishedStatistics
import kotlinx.coroutines.launch
import javax.inject.Inject

open class EndGame @Inject constructor(
    private val scope: ApplicationScope,
    private val currentGame: CurrentGame,
    private val repository: GameStatusRepository,
    private val timeCounter: TimeCounter,
    private val updateStatistics: UpdateGameFinishedStatistics,
    private val isNewBestTime: IsNewBestTime,
) {
    open operator fun invoke() {
        timeCounter.stop()
        currentGame.onGameFinished()

        val gameId = currentGame.id
        val currentGameDifficulty = currentGame.getCurrentBoard().difficulty
        val currentGameTimeInSeconds = timeCounter.getCurrentTime()
        scope.launch {
            setLastGameFinishedSummary(gameId, currentGameDifficulty, currentGameTimeInSeconds)

            updateStatistics(currentGameDifficulty, currentGameTimeInSeconds)

            repository.removeSavedBoard()

            repository.removeGameId()
        }
    }

    private suspend fun setLastGameFinishedSummary(
        gameId: String,
        difficulty: Difficulty,
        gameTimeInSeconds: Long?,
    ) {
        val finishedGameSummary = LastFinishedGameSummary(
            gameId = gameId,
            isNewBestTime = isNewBestTime(difficulty, gameTimeInSeconds),
            gameTimeInSeconds = gameTimeInSeconds,
        )

        repository.setLastFinishedGameSummary(finishedGameSummary)
    }
}