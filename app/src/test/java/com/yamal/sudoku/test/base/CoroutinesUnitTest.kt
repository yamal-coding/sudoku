package com.yamal.sudoku.test.base

import com.yamal.sudoku.test.overrides.CoroutinesTestDispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
abstract class CoroutinesUnitTest {

    private val testDispatcher = TestCoroutineDispatcher()
    val dispatcherProvider = CoroutinesTestDispatcherProvider(testDispatcher)

    @Before
    fun setUpTestDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun resetTestDispatcher() {
        testDispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}