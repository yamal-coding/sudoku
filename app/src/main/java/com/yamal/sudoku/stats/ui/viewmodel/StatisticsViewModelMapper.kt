package com.yamal.sudoku.stats.ui.viewmodel

import com.yamal.sudoku.commons.ui.TimeCounterFormatter
import com.yamal.sudoku.stats.domain.GameStatistics
import com.yamal.sudoku.stats.domain.GameStatisticsByDifficulty
import javax.inject.Inject

class StatisticsViewModelMapper @Inject constructor(
    private val timeCounterFormatter: TimeCounterFormatter,
) {
    fun toViewModel(statisticsByDifficulty: GameStatisticsByDifficulty): GameStatisticsByDifficultyViewData =
        with (statisticsByDifficulty) {
            GameStatisticsByDifficultyViewData(
                easyStatistics = easyStatistics.toViewData(),
                mediumStatistics = mediumStatistics.toViewData(),
                hardStatistics = hardStatistics.toViewData(),
            )
        }

    fun GameStatistics.toViewData(): GameStatisticsViewData =
        GameStatisticsViewData(
            bestTimeInSeconds = bestTimeInSeconds?.let { timeCounterFormatter.format(it) },
            gamesPlayed = gamesPlayed,
            gamesWon = gamesWon,
        )
}
