package com.yamal.sudoku.test

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class BaseTest<T : Activity>(testClass: Class<T>) {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityTestRule = ActivityTestRule(testClass, false, false)

    @Inject
    lateinit var clearStorages: ClearStorages

    @Before
    fun init() {
        hiltRule.inject()
        clearStorages.invoke()
    }

    fun launchTarget() {
        activityTestRule.launchActivity(null)
    }
}