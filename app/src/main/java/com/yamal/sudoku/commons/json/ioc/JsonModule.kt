package com.yamal.sudoku.commons.json.ioc

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class JsonModule {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder().build()
}