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
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoadSavedBoardTest : UnitTest() {

    private val repository: GameStatusRepository = mock()
    private val currentGame: CurrentGame = mock()
    private val gameFactory: GameFactory = mock()
    private val timeCounter: TimeCounter = mock()
    private val loadSavedBoard = LoadSavedBoard(
        repository,
        currentGame,
        gameFactory,
        timeCounter,
    )

    @Before
    fun setUp() {
        givenGameHasStarted()
    }

    @Test
    fun `should load existing saved game`() = runTest {
        givenASavedBoard()
        givenAGame()
        givenSomeSavedTimeCount()

        loadSavedBoard(SOME_GAME_ID)

        verify(currentGame).onLoadingGame()
        verify(currentGame).onGameReady(ANY_GAME)
        verify(timeCounter).start(ANY_SECONDS)
    }

    @Test
    fun `should not start timer when there is no previous time saved`() = runTest {
        givenASavedBoard()
        givenAGame()
        givenNoSavedTimeCount()

        loadSavedBoard(SOME_GAME_ID)

        verify(timeCounter, never()).start(any())
    }

    @Test
    fun `should not load existing saved game`() = runTest {
        givenThereIsNoSavedBoard()

        loadSavedBoard(SOME_GAME_ID)

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
        whenever(gameFactory.get(SOME_GAME_ID, ANY_BOARD)).thenReturn(ANY_GAME)
    }

    private suspend fun givenSomeSavedTimeCount() {
        whenever(repository.getTimeCounterSync()).thenReturn(ANY_SECONDS)
    }

    private suspend fun givenNoSavedTimeCount() {
        whenever(repository.getTimeCounterSync()).thenReturn(null)
    }

    private companion object {
        const val SOME_GAME_ID = "1234"
        val ANY_BOARD = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val ANY_GAME = Game(SOME_GAME_ID, ANY_BOARD)
        const val ANY_SECONDS = 1345L
    }
}
