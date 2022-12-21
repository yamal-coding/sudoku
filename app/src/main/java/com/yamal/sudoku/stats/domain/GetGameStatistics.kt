package com.yamal.sudoku.stats.domain

import com.yamal.sudoku.commons.thread.di.DefaultDispatcher
import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.stats.data.StatisticsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

open class GetGameStatistics @Inject constructor(
    private val gameStatusRepository: GameStatusRepository,
    private val statisticsRepository: StatisticsRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    open operator fun invoke(): Flow<GameStatisticsByDifficulty> = combine(
        getStats(Difficulty.EASY),
        getStats(Difficulty.MEDIUM),
        getStats(Difficulty.HARD),
    ) { easyStats, mediumStats, hardStats ->
        GameStatisticsByDifficulty(
            easyStatistics = easyStats,
            mediumStatistics = mediumStats,
            hardStatistics = hardStats,
        )
    }.flowOn(defaultDispatcher)

    private fun getStats(difficulty: Difficulty): Flow<GameStatistics> = combine(
        gameStatusRepository.getSavedBoard(),
        statisticsRepository.getBestTimeInSeconds(difficulty),
        statisticsRepository.getGamesPlayed(difficulty),
        statisticsRepository.getGamesWon(difficulty),
    ) { savedBoard, bestTimeInSeconds, gamesPlayed, gamesWon ->
        val getActualPlayedGames = gamesPlayed
            ?: getActualPlayedGamesWhenThereArentPreviousStats(difficulty, savedBoard?.difficulty)
        GameStatistics(
            bestTimeInSeconds = bestTimeInSeconds,
            gamesPlayed = getActualPlayedGames,
            gamesWon = gamesWon ?: 0,
        )
    }

    // If there aren't any previous statistics but there is a current game in progress,
    // we return 1 as the number of played games and update the stats.
    // This scenario can take place after having updated the app but the user still has an in progress game
    private suspend fun getActualPlayedGamesWhenThereArentPreviousStats(
        difficulty: Difficulty,
        savedBoardDifficulty: Difficulty?
    ): Long =
        if (difficulty == savedBoardDifficulty) {
            val playedGames = 1L
            statisticsRepository.setGamesPlayed(difficulty, playedGames)
            playedGames
        } else {
            0
        }
}
