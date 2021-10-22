package com.yamal.sudoku.game.level.data

import com.yamal.sudoku.game.level.domain.Level
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.Difficulty
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LevelsRepository @Inject constructor(
    private val dataSource: LevelsDataSource
) {
    fun getNewLevel(): Board? =
        dataSource.getNewLevel()

    fun getNewLevelTemp(difficulty: Difficulty): Level? =
        dataSource.getNewLevel(difficulty)?.let { levelDO ->
            Level(
                id = levelDO.id,
                difficulty = levelDO.difficulty,
                board = rawLevelToBoard(levelDO.rawBoard, levelDO.difficulty) ?: return null
            )
        }
}
