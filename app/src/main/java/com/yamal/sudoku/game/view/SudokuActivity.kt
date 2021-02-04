package com.yamal.sudoku.game.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.yamal.sudoku.R
import com.yamal.sudoku.game.presenter.SudokuPresenter
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCellValue
import org.koin.android.ext.android.inject

class SudokuActivity : AppCompatActivity(), SudokuView {

    private val presenter: SudokuPresenter by inject()

    private lateinit var board: SudokuBoardView
    private lateinit var startGameButton: Button
    private lateinit var saveGameButton: Button
    private lateinit var checkGameButton: Button
    private lateinit var buttonsLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku)

        bindViews()
        setUpListeners()

        val isNewGame = intent.getBooleanExtra(IS_NEW_GAME_EXTRA, false)
        presenter.onCreate(isNewGame, this)
    }

    private fun bindViews() {
        board = findViewById(R.id.sudoku_board)
        startGameButton = findViewById(R.id.start_game)
        saveGameButton = findViewById(R.id.save_game)
        checkGameButton = findViewById(R.id.check_game)
        buttonsLayout = findViewById(R.id.buttons_layout)
    }

    private fun setUpListeners() {
        board.listener = object : SudokuBoardView.OnCellSelectedListener {
            override fun onCellSelected(x: Int, y: Int) {
                presenter.onCellSelected(x, y)
            }
        }

        val cellButtons = mapOf(
            R.id.clear_button to SudokuCellValue.EMPTY,
            R.id.one_button to SudokuCellValue.ONE,
            R.id.two_button to SudokuCellValue.TWO,
            R.id.three_button to SudokuCellValue.THREE,
            R.id.four_button to SudokuCellValue.FOUR,
            R.id.five_button to SudokuCellValue.FIVE,
            R.id.six_button to SudokuCellValue.SIX,
            R.id.seven_button to SudokuCellValue.SEVEN,
            R.id.eight_button to SudokuCellValue.EIGHT,
            R.id.nine_button to SudokuCellValue.NINE,
        )

        cellButtons.forEach { cellButton ->
            findViewById<Button>(cellButton.key).setOnClickListener {
                presenter.selectNumber(cellButton.value)
            }
        }

        findViewById<Button>(R.id.start_game).setOnClickListener { presenter.setUpFinishedGame() }
        findViewById<Button>(R.id.check_game).setOnClickListener { presenter.checkGame() }
        findViewById<Button>(R.id.save_game).setOnClickListener { presenter.saveGame() }
    }

    override fun onNewGame() {
        startGameButton.visibility = View.VISIBLE
    }

    override fun onSavedGame() {
        findViewById<Button>(R.id.check_game).visibility = View.VISIBLE
        findViewById<Button>(R.id.save_game).visibility = View.VISIBLE
    }

    override fun onResetGame(onlyBoard: ReadOnlyBoard) {
        board.unHighlightBackground()
        board.setBoard(onlyBoard)
    }

    override fun updateBoard(onlyBoard: ReadOnlyBoard) {
        board.setBoard(onlyBoard)
    }

    override fun onSetUpFinished() {
        startGameButton.visibility = View.GONE
        checkGameButton.visibility = View.VISIBLE
        saveGameButton.visibility = View.VISIBLE
    }

    override fun onGameFinished() {
        board.highlightBackground()
        checkGameButton.visibility = View.GONE
        buttonsLayout.visibility = View.GONE
        saveGameButton.visibility = View.GONE
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    companion object {
        const val IS_NEW_GAME_EXTRA = "is_new_game"
    }
}
