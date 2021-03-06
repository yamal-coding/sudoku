package com.yamal.sudoku.game.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.yamal.sudoku.R
import com.yamal.sudoku.game.viewmodel.SudokuViewModel
import com.yamal.sudoku.game.viewmodel.SudokuViewState
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCellValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SudokuActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: SudokuViewModel

    private lateinit var board: SudokuBoardView
    private lateinit var startGameButton: Button
    private lateinit var removeCellButton: Button
    private lateinit var toolbar: Toolbar
    private lateinit var buttonsLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku)

        setUpToolbar()
        bindViews()
        setUpListeners()

        viewModel.onCreate(
            isSetUpNewGameMode = intent.getBooleanExtra(IS_SET_UP_GAME_MODE_EXTRA, false),
            isNewGame = intent.getBooleanExtra(IS_NEW_GAME_EXTRA, false)
        )

        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is SudokuViewState.Loading -> { /* Nothing to do */ }
                    is SudokuViewState.SettingUpNewGame -> onSettingUpNewGame(it.initialBoard)
                    is SudokuViewState.NewGameLoaded -> onNewGameLoaded(it.board)
                    is SudokuViewState.UpdateBoard -> updateBoard(it.board)
                    is SudokuViewState.SetUpFinished -> onSetUpFinished()
                    is SudokuViewState.GameFinished -> onGameFinished()
                }
            }
        }
    }

    private fun setUpToolbar() {
        toolbar = findViewById(R.id.tool_bar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun bindViews() {
        board = findViewById(R.id.sudoku_board)
        startGameButton = findViewById(R.id.start_game)
        removeCellButton = findViewById(R.id.remove_cell_button)
        buttonsLayout = findViewById(R.id.buttons_layout)
    }

    private fun setUpListeners() {
        board.listener = object : SudokuBoardView.OnCellSelectedListener {
            override fun onCellSelected(x: Int, y: Int) {
                viewModel.onCellSelected(x, y)
            }
        }

        val cellButtons = mapOf(
            R.id.remove_cell_button to SudokuCellValue.EMPTY,
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
                viewModel.selectNumber(cellButton.value)
            }
        }
    }

    private fun onSettingUpNewGame(initialBoard: ReadOnlyBoard) {
        setTitle(getString(R.string.set_up_new_game_mode_title))
        with(startGameButton) {
            visibility = View.VISIBLE
            setOnClickListener { viewModel.finishSetUpAndStartGame() }
        }
        removeCellButton.visibility =  View.VISIBLE
        updateBoard(initialBoard)
    }

    private fun onNewGameLoaded(board: ReadOnlyBoard) {
        updateBoard(board)
        removeCellButton.visibility =  View.VISIBLE
    }

    private fun updateBoard(onlyBoard: ReadOnlyBoard) {
        board.setBoard(onlyBoard)
    }

    private fun onSetUpFinished() {
        startGameButton.visibility = View.GONE
    }

    private fun onGameFinished() {
        board.highlightBackground()
        buttonsLayout.visibility = View.GONE
        removeCellButton.visibility = View.GONE
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun setTitle(title: String) {
        toolbar.title = title
    }

    companion object {
        const val IS_SET_UP_GAME_MODE_EXTRA = "is_set_up_game_mode"
        const val IS_NEW_GAME_EXTRA = "is_new_game"
    }
}
