package com.yamal.sudoku.game.level.domain

import com.yamal.sudoku.game.level.data.LevelsRepository
import com.yamal.sudoku.model.Difficulty
import javax.inject.Inject

class LoadNewBoard @Inject constructor(
    private val levelsRepository: LevelsRepository
) {
    // TODO correctly specify difficulty from UI
    suspend operator fun invoke(): Level? = levelsRepository.getNewLevel(Difficulty.EASY)
}