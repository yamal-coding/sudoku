package com.yamal.sudoku.game.level.domain

import com.yamal.sudoku.game.level.data.LevelsRepository
import com.yamal.sudoku.model.Board
import javax.inject.Inject

class LoadNewBoard @Inject constructor(
    private val levelsRepository: LevelsRepository
) {
    operator fun invoke(): Board? = levelsRepository.getNewLevel()
}