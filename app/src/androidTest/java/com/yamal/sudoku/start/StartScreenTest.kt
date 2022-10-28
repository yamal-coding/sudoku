package com.yamal.sudoku.start

import com.yamal.sudoku.test.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class StartScreenTest : BaseTest() {

    @Test
    fun test() {
        onStartScreen()
    }
}