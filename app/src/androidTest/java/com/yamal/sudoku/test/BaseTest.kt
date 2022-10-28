package com.yamal.sudoku.test

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import com.yamal.sudoku.main.ui.MainActivity
import com.yamal.sudoku.start.screen.StartTestScreen
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@Suppress("UnnecessaryAbstractClass")
abstract class BaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createEmptyComposeRule()

    @Inject
    lateinit var clearStorages: ClearStorages

    @Before
    fun init() {
        hiltRule.inject()
        clearStorages.invoke()
    }

    fun onStartScreen(): StartTestScreen {
        launchTarget()
        return StartTestScreen(composeRule)
    }

    private fun launchTarget() {
        ActivityScenario.launch(MainActivity::class.java)
            .moveToState(Lifecycle.State.RESUMED)
    }
}
