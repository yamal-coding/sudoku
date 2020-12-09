package com.yamal.sudoku.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.JobDispatcherImpl
import com.yamal.sudoku.presenter.SudokuPresenter
import com.yamal.sudoku.repository.BoardRepository
import com.yamal.sudoku.storage.BoardStorage
import com.yamal.sudoku.usecase.GetSavedBoard
import com.yamal.sudoku.usecase.SaveBoard
import kotlinx.android.synthetic.main.activity_sudoku.*

class SudokuActivity : AppCompatActivity(), SudokuView {

    private lateinit var presenter: SudokuPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku)

        injectDependencies()

        setUpListeners()

        presenter.onCreate(this)
    }

    private fun injectDependencies() {
        val gson = GsonBuilder().create()
        val sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_file_name), MODE_PRIVATE)
        val storage = BoardStorage(gson, sharedPreferences)
        val jobDispatcher = JobDispatcherImpl()
        val repository = BoardRepository(storage, jobDispatcher)

        presenter = SudokuPresenter(GetSavedBoard(repository), SaveBoard(repository), jobDispatcher)
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

        start_game.setOnClickListener { presenter.setUpFinishedGame() }
        check_game.setOnClickListener { presenter.checkGame() }
        save_game.setOnClickListener { presenter.saveGame() }
    }

    override fun onNewGame() {
        start_game.visibility = View.VISIBLE
    }

    override fun onSavedGame() {
        check_game.visibility = View.VISIBLE
        save_game.visibility = View.VISIBLE
    }

    override fun onResetGame(onlyBoard: ReadOnlyBoard) {
        sudoku_board.unHighlightBackground()
        sudoku_board.setBoard(onlyBoard)
    }

    override fun updateBoard(onlyBoard: ReadOnlyBoard) {
        sudoku_board.setBoard(onlyBoard)
    }

    override fun onSetUpFinished() {
        start_game.visibility = View.GONE
        check_game.visibility = View.VISIBLE
        save_game.visibility = View.VISIBLE
    }

    override fun onGameFinished() {
        sudoku_board.highlightBackground()
        check_game.visibility = View.GONE
        buttons_layout.visibility = View.GONE
        save_game.visibility = View.GONE
    }
}