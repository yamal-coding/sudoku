package com.yamal.sudoku.game.level.data

import com.yamal.sudoku.commons.thread.di.IODispatcher
import com.yamal.sudoku.game.level.data.datasource.LevelsDataSource
import com.yamal.sudoku.game.level.domain.Level
import com.yamal.sudoku.model.Difficulty
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LevelsRepository @Inject constructor(
    private val dataSource: LevelsDataSource,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun getNewLevel(difficulty: Difficulty): Level? = withContext(ioDispatcher) {
        dataSource.getNewLevel(difficulty)?.let { levelDO ->
            Level(
                id = levelDO.id,
                difficulty = levelDO.difficulty,
                board = rawLevelToBoard(levelDO.rawBoard) ?: return@let null
            )
        }
    }
}
