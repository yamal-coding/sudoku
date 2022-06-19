package com.yamal.sudoku.main.domain

import com.yamal.sudoku.commons.thread.di.IODispatcher
import com.yamal.sudoku.game.status.data.GameStatusRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HasSavedBoard @Inject constructor(
    private val repository: GameStatusRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(): Boolean =
        repository.hasSavedBoard()

    fun observe(): Flow<Boolean> =
        repository.observeBoard().map {
            it != null
        }.flowOn(ioDispatcher)
}