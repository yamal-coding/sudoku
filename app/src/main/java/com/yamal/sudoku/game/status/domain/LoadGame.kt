package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.Difficulty
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class LoadGame @Inject constructor(
    private val gameStatusRepository: GameStatusRepository,
    private val loadNewBoard: LoadNewBoard,
    private val loadSavedBoard: LoadSavedBoard,
    private val currentGame: CurrentGame,
    private val getExistingGameInfo: GetExistingGameInfo
) {
    suspend operator fun invoke(gameId: String, difficulty: Difficulty) {
        currentGame.onGameStarted {
            val existingGameId = getExistingGameInfo().firstOrNull()?.gameId

            if (gameId == existingGameId) {
                loadSavedBoard(gameId)
            } else {
                gameStatusRepository.setGameId(gameId)
                loadNewBoard(gameId, difficulty)
            }
        }
    }
}
