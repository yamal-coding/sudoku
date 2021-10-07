package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.commons.thread.ApplicationScope
import com.yamal.sudoku.storage.BoardStorage
import com.yamal.sudoku.test.base.CoroutinesUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class GameStatusRepositoryTest : CoroutinesUnitTest() {

    private val storage: BoardStorage = mock()
    private val scope = ApplicationScope(dispatcherProvider)
    private val repository = GameStatusRepository(
        storage,
        scope,
        dispatcherProvider
    )


}