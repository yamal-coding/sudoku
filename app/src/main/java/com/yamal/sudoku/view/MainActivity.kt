package com.yamal.sudoku.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.JobDispatcherImpl
import com.yamal.sudoku.navigation.Navigator
import com.yamal.sudoku.presenter.MainPresenter
import com.yamal.sudoku.repository.BoardRepository
import com.yamal.sudoku.usecase.HasSavedBoard
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies()

        presenter.onCreate(this)
    }

    private fun injectDependencies() {
        presenter = MainPresenter(
            HasSavedBoard(BoardRepository.getInstance(this)),
            Navigator(this),
            JobDispatcherImpl()
        )
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