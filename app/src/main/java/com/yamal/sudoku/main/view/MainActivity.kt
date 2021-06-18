package com.yamal.sudoku.main.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yamal.sudoku.R
import com.yamal.sudoku.main.presenter.MainPresenter
import com.yamal.sudoku.main.presenter.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: MainPresenter

    private lateinit var loadSavedGameButton: Button
    private lateinit var newGameButton: Button
    private lateinit var setUpNewGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()

        lifecycleScope.launch {
            presenter.state.collect {
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
        setUpNewGameButton = findViewById(R.id.setup_new_game)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
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
            presenter.openSavedGame()
        }
    }

    private fun setUpOpenNewGameButtons() {
        newGameButton.setOnClickListener {
            presenter.openNewGame()
        }
        setUpNewGameButton.setOnClickListener {
            presenter.openExistingGameSetUp()
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}
