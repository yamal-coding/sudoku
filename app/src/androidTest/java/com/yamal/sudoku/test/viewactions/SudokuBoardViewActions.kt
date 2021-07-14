package com.yamal.sudoku.test.viewactions

import android.view.MotionEvent
import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.yamal.sudoku.game.view.SudokuBoardView
import org.hamcrest.Matcher

object SudokuBoardViewActions {
    fun selectCell(row: Int, column: Int): ViewAction =
        object : ViewAction {
            override fun getConstraints(): Matcher<View> = isDisplayed()

            override fun getDescription(): String = "Select cell [$row, $column] on SudokuView"

            override fun perform(uiController: UiController, view: View) {
                val sudokuBoardView = view as SudokuBoardView

                val cellHeight = sudokuBoardView.height.toFloat() / 9
                val cellWidth = sudokuBoardView.width.toFloat() / 9

                val cellToClickX = cellHeight * column - (cellHeight / 2)
                val cellToClickY = cellWidth * row  - (cellWidth / 2)

                sudokuBoardView.onTouchEvent(
                    MotionEvent.obtain(1L, 1L, MotionEvent.ACTION_DOWN, cellToClickX, cellToClickY, 0)
                )
            }
        }
}
