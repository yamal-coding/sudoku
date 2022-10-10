package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.domain.Game
import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.test.base.UnitTest
import com.yamal.sudoku.test.utils.AlmostSolvedSudokuMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoadSavedBoardTest : UnitTest() {

    private val repository: GameStatusRepository = mock()
    private val currentGame: CurrentGame = mock()
    private val gameFactory: GameFactory = mock()
    private val loadSavedBoard = LoadSavedBoard(
        repository,
        currentGame,
        gameFactory,
    )

    @Before
    fun setUp() {
        givenGameHasStarted()
    }

    @Test
    fun `should load existing saved game`() = runTest {
        givenASavedBoard()
        givenAGame()

        loadSavedBoard()

        verify(currentGame).onLoadingGame()
        verify(currentGame).onGameReady(ANY_GAME)
    }

    @Test
    fun `should not load existing saved game`() = runTest {
        givenThereIsNoSavedBoard()

        loadSavedBoard()

        verify(currentGame).onSavedGameNotFound()
    }

    private suspend fun givenASavedBoard() {
        whenever(repository.getSavedBoardSync()).thenReturn(ANY_BOARD)
    }

    private suspend fun givenThereIsNoSavedBoard() {
        whenever(repository.getSavedBoardSync()).thenReturn(null)
    }

    private fun givenGameHasStarted() {
        runBlocking {
            val captor = argumentCaptor<suspend () -> Unit>()
            whenever(currentGame.onGameStarted(captor.capture())).then {
                runBlocking {
                    captor.firstValue.invoke()
                }
            }
        }
    }

    private fun givenAGame() {
        whenever(gameFactory.get(ANY_BOARD)).thenReturn(ANY_GAME)
    }

    private companion object {
        val ANY_BOARD = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val ANY_GAME = Game(ANY_BOARD)
    }
}
