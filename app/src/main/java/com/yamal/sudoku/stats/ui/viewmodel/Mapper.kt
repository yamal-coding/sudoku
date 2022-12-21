package com.yamal.sudoku.stats.ui.viewmodel

import com.yamal.sudoku.stats.domain.GameStatistics
import com.yamal.sudoku.stats.domain.GameStatisticsByDifficulty

fun GameStatisticsByDifficulty.toViewData(): GameStatisticsByDifficultyViewData =
    GameStatisticsByDifficultyViewData(
        easyStatistics = easyStatistics.toViewData(),
        mediumStatistics = mediumStatistics.toViewData(),
        hardStatistics = hardStatistics.toViewData(),
    )

fun GameStatistics.toViewData(): GameStatisticsViewData =
    GameStatisticsViewData(
        bestTimeInSeconds = bestTimeInSeconds,
        gamesPlayed = gamesPlayed,
        gamesWon = gamesWon,
    )
