package com.yamal.sudoku

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.yamal.sudoku.main.view.MainActivity
import com.yamal.sudoku.test.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.not
import org.junit.Test

@HiltAndroidTest
class MainActivityTest : BaseTest<MainActivity>(MainActivity::class.java){

    // TODO this is just a test suite to see that espresso is working. Real tests are coming soon

    @Test
    fun testThatWillSucceed() {
        launchTarget()

        onView(withId(R.id.buttons_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun testThatWillFail() {
        launchTarget()

        onView(withId(R.id.buttons_layout)).check(matches(not(isDisplayed())))
    }
}
