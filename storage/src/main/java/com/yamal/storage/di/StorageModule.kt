package com.yamal.storage.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.yamal.storage.KeyValueStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    fun provideKeyValueStorage(
        @ApplicationContext context: Context
    ): KeyValueStorage =
        KeyValueStorage(
            sharedPreferences = context.getSharedPreferences("sudoku_board_storage", Context.MODE_PRIVATE),
            gson = GsonBuilder().create()
        )
}