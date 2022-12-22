package com.yamal.sudoku.stats.ui.viewmodel

data class GameStatisticsViewData(
    val bestTimeInSeconds: String?,
    val gamesPlayed: Long,
    val gamesWon: Long,
)

data class GameStatisticsByDifficultyViewData(
    val easyStatistics: GameStatisticsViewData,
    val mediumStatistics: GameStatisticsViewData,
    val hardStatistics: GameStatisticsViewData,
)