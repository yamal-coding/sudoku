package com.yamal.sudoku.test

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@Suppress("UnnecessaryAbstractClass")
abstract class BaseTest<T : Activity>(
    private val testClass: Class<T>
) {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var clearStorages: ClearStorages

    @Before
    fun init() {
        hiltRule.inject()
        clearStorages.invoke()
    }

    private fun launchTarget() {
        ActivityScenario.launch(testClass)
            .moveToState(Lifecycle.State.RESUMED)
    }
}
