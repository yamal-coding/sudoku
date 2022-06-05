package com.yamal.sudoku.test.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
abstract class UnitTest {

    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUpTestDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun resetTestDispatcher() {
        Dispatchers.resetMain()
    }
}