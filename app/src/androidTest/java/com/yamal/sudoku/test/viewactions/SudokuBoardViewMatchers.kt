package com.yamal.sudoku.test.viewactions

import android.view.View
import com.yamal.sudoku.game.view.SudokuBoardView
import com.yamal.sudoku.model.ReadOnlyBoard
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object SudokuBoardViewMatchers {
    fun contentIs(expectedBoard: ReadOnlyBoard): Matcher<View> =
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("has expected Sudoku board")
            }

            override fun matchesSafely(view: View): Boolean =
                view is SudokuBoardView && view.board == expectedBoard
        }
}
