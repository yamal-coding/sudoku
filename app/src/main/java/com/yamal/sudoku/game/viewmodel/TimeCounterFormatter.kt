package com.yamal.sudoku.game.viewmodel

import javax.inject.Inject

class TimeCounterFormatter @Inject constructor() {
    fun format(counter: Long): String {
        val minutes = (counter / ONE_MINUTE_IN_SECONDS).toStringWithLeadingZeroIfNeeded()
        val seconds = (counter % ONE_MINUTE_IN_SECONDS).toStringWithLeadingZeroIfNeeded()

        return "$minutes:$seconds"
    }

    private fun Long.toStringWithLeadingZeroIfNeeded(): String =
        if (this < 10) {
            "0$this"
        } else {
            toString()
        }

    private companion object {
        const val ONE_MINUTE_IN_SECONDS = 60L
    }
}
