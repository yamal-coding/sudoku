package com.yamal.sudoku.test

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import com.yamal.sudoku.main.MainScreen
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UnnecessaryAbstractClass")
abstract class BaseTest<T : Activity>(
    private val testClass: Class<T>
) {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var clearStorages: ClearStorages

    @Inject
    lateinit var mainScreenProvider: Provider<MainScreen>

    @Before
    fun init() {
        hiltRule.inject()
        clearStorages.invoke()
    }

    fun givenThatCurrentScreenIsMainScreen(): MainScreen {
        launchTarget()
        return mainScreenProvider.get()
    }

    private fun launchTarget() {
        ActivityScenario.launch(testClass)
            .moveToState(Lifecycle.State.RESUMED)
    }
}
