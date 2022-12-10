package com.yamal.sudoku.stats.domain

import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.stats.data.StatisticsRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class IncreaseGamesPlayed @Inject constructor(
    private val repository: StatisticsRepository,
) {
    suspend operator fun invoke(difficulty: Difficulty) {
        val previousNumOfGamesPlayed = repository.getGamesPlayed(difficulty).firstOrNull() ?: 0L

        repository.setGamesPlayed(difficulty, previousNumOfGamesPlayed + 1)
    }
}
