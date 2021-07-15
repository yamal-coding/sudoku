package com.yamal.sudoku.test

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.yamal.sudoku.main.MainScreen
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import javax.inject.Provider

abstract class BaseTest<T : Activity>(testClass: Class<T>) {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityTestRule = ActivityTestRule(testClass, false, false)

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
        activityTestRule.launchActivity(null)
    }
}
