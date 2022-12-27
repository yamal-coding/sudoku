package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.domain.GameConstants
import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.test.base.UnitTest
import com.yamal.sudoku.test.utils.AlmostSolvedSudokuMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetExistingGameInfoTest : UnitTest() {

    private val repository: GameStatusRepository = mock()
    private val getExistingGameInfo = GetExistingGameInfo(
        repository,
        testDispatcher,
    )

    @Test
    fun `Should return existing game info when there are existing game Id and saved board`() = runTest {
        givenAGameId(SOME_GAME_ID)
        givenASavedBoard()

        val result = getExistingGameInfo().firstOrNull()

        assertEquals(
            ExistingGameInfo(gameId = SOME_GAME_ID, difficulty = SOME_DIFFICULTY),
            result
        )
    }

    @Test
    fun `Should not return existing game when there is no saved board`() = runTest {
        givenAGameId(SOME_GAME_ID)
        givenNoSavedBoard()

        val result = getExistingGameInfo().firstOrNull()

        assertNull(result)
    }

    @Test
    fun `Should return default game Id when there is saved board but no existing game Id`() = runTest {
        givenAGameId(null)
        givenASavedBoard()

        val result = getExistingGameInfo().firstOrNull()

        assertEquals(
            ExistingGameInfo(gameId = GameConstants.DEFAULT_GAME_ID, difficulty = SOME_DIFFICULTY),
            result
        )
    }

    private fun givenAGameId(gameId: String?) {
        whenever(repository.getGameId())
            .thenReturn(flowOf(gameId))
    }

    private fun givenASavedBoard() {
        whenever(repository.getSavedBoard())
            .thenReturn(flowOf(AlmostSolvedSudokuMother.almostSolvedSudoku(difficulty = SOME_DIFFICULTY)))
    }

    private fun givenNoSavedBoard() {
        whenever(repository.getSavedBoard())
            .thenReturn(flowOf(null))
    }

    private companion object {
        const val SOME_GAME_ID = "1234"
        val SOME_DIFFICULTY = Difficulty.MEDIUM
    }
}