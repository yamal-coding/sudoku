package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.test.base.UnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoadGameTest : UnitTest() {

    private val gameStatusRepository: GameStatusRepository = mock()
    private val loadNewBoard: LoadNewBoard = mock()
    private val loadSavedBoard: LoadSavedBoard = mock()
    private val currentGame: CurrentGame = mock()
    private val getExistingGameInfo: GetExistingGameInfo = mock()
    private val loadGame = LoadGame(
        gameStatusRepository,
        loadNewBoard,
        loadSavedBoard,
        currentGame,
        getExistingGameInfo,
    )

    @Before
    fun setUp() {
        givenGameHasStarted()
    }

    @Test
    fun `Should load existing game when given game Id matches current one`() = runTest {
        whenever(getExistingGameInfo()).thenReturn(flowOf(ExistingGameInfo(SOME_GAME_ID, SOME_DIFFICULTY)))

        loadGame(SOME_GAME_ID, SOME_DIFFICULTY)

        verify(loadSavedBoard).invoke(SOME_GAME_ID)
    }

    @Test
    fun `Should load new game when given game Id doesn't match existing game Id`() = runTest {
        whenever(getExistingGameInfo()).thenReturn(flowOf(ExistingGameInfo(OTHER_GAME_ID, OTHER_DIFFICULTY)))

        loadGame(SOME_GAME_ID, SOME_DIFFICULTY)

        verify(loadNewBoard).invoke(SOME_GAME_ID, SOME_DIFFICULTY)
    }

    @Test
    fun `Should load new game when there is no existing game Id`() = runTest {
        whenever(getExistingGameInfo()).thenReturn(flowOf(null))

        loadGame(SOME_GAME_ID, SOME_DIFFICULTY)

        verify(loadNewBoard).invoke(SOME_GAME_ID, SOME_DIFFICULTY)
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

    private companion object {
        const val SOME_GAME_ID = "1234"
        const val OTHER_GAME_ID = "5678"
        val SOME_DIFFICULTY = Difficulty.EASY
        val OTHER_DIFFICULTY = Difficulty.MEDIUM
    }
}