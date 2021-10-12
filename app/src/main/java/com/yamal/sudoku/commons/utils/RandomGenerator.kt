package com.yamal.sudoku.commons.utils

import javax.inject.Inject
import kotlin.random.Random

open class RandomGenerator @Inject constructor() {
    open fun randomInt(from: Int, until: Int): Int =
        Random.nextInt(from = from, until = until)
}
