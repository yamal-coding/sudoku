package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.domain.Game
import com.yamal.sudoku.game.level.data.LevelsRepository
import com.yamal.sudoku.game.level.domain.Level
import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.Difficulty
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
class LoadNewBoardTest : UnitTest() {

    private val gameStatusRepository: GameStatusRepository = mock()
    private val levelsRepository: LevelsRepository = mock()
    private val currentGame: CurrentGame = mock()
    private val gameFactory: GameFactory = mock()
    private val loadNewBoard = LoadNewBoard(
        gameStatusRepository,
        levelsRepository,
        currentGame,
        gameFactory,
    )

    @Before
    fun setUp() {
        givenGameHasStarted()
    }

    @Test
    fun `load new level when game is started`() = runTest {
        givenANewLevel()
        givenAGame()

        loadNewBoard(ANY_DIFFICULTY)

        verify(levelsRepository).markLevelAsAlreadyReturned(ANY_LEVEL_ID)
        verify(currentGame).onGameReady(ANY_GAME)
        verify(gameStatusRepository).saveBoard(ANY_BOARD)
    }

    @Test
    fun `should not load any new level`() = runTest {
        givenThereIsNoNewLevel()

        loadNewBoard(ANY_DIFFICULTY)

        verify(currentGame).onNewBoardNotFound()
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

    private suspend fun givenANewLevel() {
        whenever(levelsRepository.getNewLevel(ANY_DIFFICULTY)).thenReturn(
            Level(
                id = ANY_LEVEL_ID,
                difficulty = ANY_DIFFICULTY,
                board = ANY_BOARD,
            )
        )
    }

    private suspend fun givenThereIsNoNewLevel() {
        whenever(levelsRepository.getNewLevel(ANY_DIFFICULTY)).thenReturn(null)
    }

    private fun givenAGame() {
        whenever(gameFactory.get(ANY_BOARD)).thenReturn(ANY_GAME)
    }

    private companion object {
        const val ANY_LEVEL_ID = "1"
        val ANY_DIFFICULTY = Difficulty.HARD
        val ANY_BOARD = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val ANY_GAME = Game(ANY_BOARD)
    }
}
