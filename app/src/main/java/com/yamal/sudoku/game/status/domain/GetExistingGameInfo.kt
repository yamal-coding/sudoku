package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.commons.thread.di.DefaultDispatcher
import com.yamal.sudoku.game.domain.GameConstants
import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.start.domain.ExistingGameInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

open class GetExistingGameInfo @Inject constructor(
    private val repository: GameStatusRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    open operator fun invoke(): Flow<ExistingGameInfo?> =
        combine(repository.getGameId(), repository.getSavedBoard()) { existingGameId, existingBoard ->
            existingBoard?.let {
                ExistingGameInfo(
                    gameId = existingGameId ?: GameConstants.DEFAULT_GAME_ID,
                    difficulty = existingBoard.difficulty,
                )
            }
        }.flowOn(defaultDispatcher)
}
