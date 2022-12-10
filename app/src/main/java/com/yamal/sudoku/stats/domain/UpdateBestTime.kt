package com.yamal.sudoku.stats.domain

import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.stats.data.StatisticsRepository
import javax.inject.Inject

class UpdateBestTime @Inject constructor(
    private val repository: StatisticsRepository,
) {
    suspend operator fun invoke(difficulty: Difficulty, newBestTimeInSeconds: Long) {
        repository.setBestTime(difficulty, newBestTimeInSeconds)
    }
}
