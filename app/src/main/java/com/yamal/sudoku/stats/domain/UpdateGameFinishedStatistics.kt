package com.yamal.sudoku.stats.domain

import com.yamal.sudoku.commons.thread.ApplicationScope
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.stats.data.StatisticsRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

open class UpdateGameFinishedStatistics @Inject constructor(
    private val statisticsRepository: StatisticsRepository,
    private val scope: ApplicationScope,
    private val isNewBestTime: IsNewBestTime,
    private val increaseGamesWon: IncreaseGamesWon
) {
    open operator fun invoke(difficulty: Difficulty, gameTimeInSeconds: Long) {
        scope.launch {
            updateCandidateBestTime(difficulty, gameTimeInSeconds)
            updateGamesPlayedIfThereArentPreviousStatistics(difficulty)
            increaseGamesWon(difficulty)
        }
    }

    private suspend fun updateCandidateBestTime(difficulty: Difficulty, gameTimeInSeconds: Long) {
        if (isNewBestTime(difficulty, gameTimeInSeconds)) {
            statisticsRepository.setBestTime(difficulty, gameTimeInSeconds)
        }
    }

    private suspend fun updateGamesPlayedIfThereArentPreviousStatistics(difficulty: Difficulty) {
        val currentGamesPlayed = statisticsRepository.getGamesPlayed(difficulty).firstOrNull()
        if (currentGamesPlayed == null) {
            statisticsRepository.setGamesPlayed(difficulty, 1)
        }
    }
}
