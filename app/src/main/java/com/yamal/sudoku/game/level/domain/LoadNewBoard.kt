package com.yamal.sudoku.game.level.domain

import com.yamal.sudoku.game.level.data.LevelsRepository
import com.yamal.sudoku.model.Difficulty
import javax.inject.Inject

class LoadNewBoard @Inject constructor(
    private val levelsRepository: LevelsRepository
) {
    suspend operator fun invoke(difficulty: Difficulty): Level? =
        levelsRepository.getNewLevel(difficulty)?.also {
            levelsRepository.markLevelAsAlreadyReturned(it.id)
        }
}