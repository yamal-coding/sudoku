package com.yamal.sudoku.main.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yamal.sudoku.R
import com.yamal.sudoku.main.viewmodel.MainViewModel
import com.yamal.sudoku.main.viewmodel.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var loadSavedGameButton: Button
    private lateinit var newGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()

        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is MainViewState.LoadingGame -> { /* Nothing to do */ }
                    is MainViewState.SavedGame -> onSavedGame()
                    is MainViewState.NotSavedGame -> onNotSavedGame()
                }
            }
        }
    }

    private fun bindViews() {
        loadSavedGameButton = findViewById(R.id.load_saved_game_button)
        newGameButton = findViewById(R.id.new_board_button)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    private fun onSavedGame() {
        setUpOpenSavedGameButton()
        setUpOpenNewGameButtons()
    }

    private fun onNotSavedGame() {
        loadSavedGameButton.visibility = View.GONE
        setUpOpenNewGameButtons()
    }

    private fun setUpOpenSavedGameButton() {
        loadSavedGameButton.visibility = View.VISIBLE
        loadSavedGameButton.setOnClickListener {
            viewModel.openSavedGame()
        }
    }

    private fun setUpOpenNewGameButtons() {
        newGameButton.setOnClickListener {
            viewModel.openNewGame()
        }
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }
}
