package com.yamal.sudoku.commons.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes

fun Context.getColorFromAttr(@AttrRes attr: Int): Int = TypedValue().run {
    theme.resolveAttribute(attr, this, true)
    data
}