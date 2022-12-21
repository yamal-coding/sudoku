package com.yamal.sudoku.stats.domain

data class GameStatistics(
    val bestTimeInSeconds: Long?,
    val gamesPlayed: Long,
    val gamesWon: Long,
)

data class GameStatisticsByDifficulty(
    val easyStatistics: GameStatistics,
    val mediumStatistics: GameStatistics,
    val hardStatistics: GameStatistics,
)
