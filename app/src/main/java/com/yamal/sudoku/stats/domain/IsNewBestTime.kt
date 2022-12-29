package com.yamal.sudoku.stats.domain

import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.stats.data.StatisticsRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

open class IsNewBestTime @Inject constructor(
    private val repository: StatisticsRepository,
) {
    open suspend operator fun invoke(difficulty: Difficulty, candidateBestTimeInSeconds: Long?): Boolean {
        if (candidateBestTimeInSeconds == null) {
            return false
        }

        val previousBestTime = repository.getBestTimeInSeconds(difficulty).firstOrNull()

        return previousBestTime == null || candidateBestTimeInSeconds < previousBestTime
    }
}
