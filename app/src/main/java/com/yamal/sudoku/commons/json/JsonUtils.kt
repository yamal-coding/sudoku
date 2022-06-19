package com.yamal.sudoku.commons.json

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import javax.inject.Inject

open class JsonUtils @Inject constructor(
    private val moshi: Moshi
) {
    @Suppress("SwallowedException")
    open fun <T> fromJsonOrNull(jsonString: String, type: Class<T>): T? =
        try {
            moshi.adapter(type).fromJson(jsonString)
        } catch (e: JsonDataException) {
            null
        }

    open fun <T> toJson(data: T, type: Class<T>): String? =
        moshi.adapter(type).toJson(data)
}