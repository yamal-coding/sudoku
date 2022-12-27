package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.commons.thread.di.DefaultDispatcher
import com.yamal.sudoku.game.status.data.GameStatusRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFinishedGameSummary @Inject constructor(
    private val gameStatusRepository: GameStatusRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(gameId: String): Flow<LastFinishedGameSummary?> =
        gameStatusRepository.getLastFinishedGameSummary().filter {
            it?.gameId == gameId
        }.flowOn(defaultDispatcher)
}
