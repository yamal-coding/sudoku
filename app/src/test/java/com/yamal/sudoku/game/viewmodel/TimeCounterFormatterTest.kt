package com.yamal.sudoku.game.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Test

class TimeCounterFormatterTest {

    private val formatter = TimeCounterFormatter()

    @Test
    fun `should format one digit seconds without minutes`() {
        assertEquals("00:03", formatter.format(seconds = 3L))
    }

    @Test
    fun `should format two digit seconds without minutes`() {
        assertEquals("00:10", formatter.format(seconds = 10L))
    }

    @Test
    fun `should format one digit seconds with one digit minutes`() {
        assertEquals("01:03", formatter.format(seconds = 63L))
    }

    @Test
    fun `should format two digit seconds with one digit minutes`() {
        assertEquals("01:13", formatter.format(seconds = 73L))
    }

    @Test
    fun `should format two digit seconds with two digit minutes`() {
        assertEquals("10:10", formatter.format(seconds = 610L))
    }

    @Test
    fun `should format more thn two digits minutes`() {
        assertEquals("100:00", formatter.format(seconds = 6000L))
    }
}
