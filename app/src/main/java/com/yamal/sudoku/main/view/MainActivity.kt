package com.yamal.sudoku.main.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yamal.sudoku.R
import com.yamal.sudoku.main.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), MainView {

    private val presenter: MainPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onCreate(this)
    }

    override fun onSavedGame() {
        setUpOpenSavedGameButton()
        setUpOpenNewGameButton()
    }

    override fun onNotSavedGame() {
        load_saved_game_button.visibility = View.GONE
        setUpOpenNewGameButton()
    }

    private fun setUpOpenSavedGameButton() {
        load_saved_game_button.visibility = View.VISIBLE
        load_saved_game_button.setOnClickListener {
            presenter.openSavedGame()
        }
    }

    private fun setUpOpenNewGameButton() {
        new_board_button.visibility = View.VISIBLE
        new_board_button.setOnClickListener {
            presenter.openNewGame()
        }
    }
}