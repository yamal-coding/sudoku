package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.commons.thread.di.IODispatcher
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.status.data.GameStatusRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSavedBoard @Inject constructor(
    private val repository: GameStatusRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(): Board? = withContext(ioDispatcher) {
        repository.getSavedBoard().firstOrNull()
    }
}