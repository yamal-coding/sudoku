package com.yamal.sudoku.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.R
import com.yamal.sudoku.presenter.SudokuPresenter
import kotlinx.android.synthetic.main.activity_sudoku.*

class SudokuActivity : AppCompatActivity(), SudokuView {

    private val presenter = SudokuPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku)

        setUpListeners()

        presenter.onCreate(this)
    }

    private fun setUpListeners() {
        sudoku_board.listener = object : SudokuBoardView.OnCellSelectedListener {
            override fun onCellSelected(x: Int, y: Int) {
                presenter.onCellSelected(x, y)
            }
        }

        clear_button.setOnClickListener { presenter.selectNumber(SudokuCellValue.EMPTY) }
        one_button.setOnClickListener { presenter.selectNumber(SudokuCellValue.ONE) }
        two_button.setOnClickListener { presenter.selectNumber(SudokuCellValue.TWO) }
        three_button.setOnClickListener { presenter.selectNumber(SudokuCellValue.THREE) }
        four_button.setOnClickListener { presenter.selectNumber(SudokuCellValue.FOUR) }
        five_button.setOnClickListener { presenter.selectNumber(SudokuCellValue.FIVE) }
        six_button.setOnClickListener { presenter.selectNumber(SudokuCellValue.SIX) }
        seven_button.setOnClickListener { presenter.selectNumber(SudokuCellValue.SEVEN) }
        eight_button.setOnClickListener { presenter.selectNumber(SudokuCellValue.EIGHT) }
        nine_button.setOnClickListener { presenter.selectNumber(SudokuCellValue.NINE) }

        start_game.setOnClickListener { presenter.startGame() }
        check_game.setOnClickListener { presenter.checkGame() }
    }

    override fun onResetGame(board: Board) {
        sudoku_board.unHighlightBackground()
        sudoku_board.setBoard(board)
    }

    override fun updateBoard(board: Board) {
        sudoku_board.setBoard(board)
    }

    override fun onGameStarted() {
        start_game.visibility = View.GONE
        check_game.visibility = View.VISIBLE
    }

    override fun onGameFinished() {
        sudoku_board.highlightBackground()
        check_game.visibility = View.GONE
        buttons_layout.visibility = View.GONE
    }
}