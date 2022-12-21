package com.yamal.sudoku.stats.data

import com.yamal.sudoku.commons.thread.ApplicationScope
import com.yamal.sudoku.commons.thread.di.IODispatcher
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.stats.data.storage.StatisticsStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

open class StatisticsRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val scope: ApplicationScope,
    private val storage: StatisticsStorage
) {
    open fun getBestTimeInSeconds(difficulty: Difficulty): Flow<Long?> =
        storage.getBestTimeInSeconds(difficulty)

    open suspend fun setBestTime(difficulty: Difficulty, bestTimeInSeconds: Long) {
        scope.launch(ioDispatcher) {
            storage.setBestTime(difficulty, bestTimeInSeconds)
        }
    }

    open fun getGamesPlayed(difficulty: Difficulty): Flow<Long?> =
        storage.getGamesPlayed(difficulty)

    open suspend fun setGamesPlayed(difficulty: Difficulty, gamesPlayed: Long) {
        scope.launch(ioDispatcher) {
            storage.setGamesPlayed(difficulty, gamesPlayed)
        }
    }

    open fun getGamesWon(difficulty: Difficulty): Flow<Long?> =
        storage.getGamesWon(difficulty)

    open suspend fun setGamesWon(difficulty: Difficulty, gamesWon: Long) {
        scope.launch(ioDispatcher) {
            storage.setGamesWon(difficulty, gamesWon)
        }
    }
}
