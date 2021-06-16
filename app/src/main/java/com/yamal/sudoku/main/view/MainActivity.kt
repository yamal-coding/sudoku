package com.yamal.sudoku.main.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.yamal.sudoku.R
import com.yamal.sudoku.main.presenter.MainPresenter
import dagger.hilt.android.AndroidEntryPoint
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    private lateinit var loadSavedGameButton: Button
    private lateinit var newGameButton: Button
    private lateinit var setUpNewGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()

        presenter.onCreate(this)
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

    override fun onSavedGame() {
        setUpOpenSavedGameButton()
        setUpOpenNewGameButtons()
    }

    override fun onNotSavedGame() {
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
