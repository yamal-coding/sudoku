package com.yamal.sudoku.commons.ui

import javax.inject.Inject

class TimeCounterFormatter @Inject constructor() {
    fun format(seconds: Long): String {
        val minutes = (seconds / ONE_MINUTE_IN_SECONDS).toStringWithLeadingZeroIfNeeded()
        val remainingSeconds = (seconds % ONE_MINUTE_IN_SECONDS).toStringWithLeadingZeroIfNeeded()

        return "$minutes:$remainingSeconds"
    }

    private fun Long.toStringWithLeadingZeroIfNeeded(): String =
        if (this < FIRST_NUMBER_WITH_TWO_DIGITS) {
            "0$this"
        } else {
            toString()
        }

    private companion object {
        const val ONE_MINUTE_IN_SECONDS = 60L
        const val FIRST_NUMBER_WITH_TWO_DIGITS = 10L
    }
}
