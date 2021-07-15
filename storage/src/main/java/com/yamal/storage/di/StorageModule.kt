package com.yamal.storage.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.yamal.storage.KeyValueStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences("sudoku_board_storage", Context.MODE_PRIVATE)

    @Provides
    fun provideKeyValueStorage(
        @ApplicationContext context: Context,
        sharedPreferences: SharedPreferences
    ): KeyValueStorage =
        KeyValueStorage(
            sharedPreferences = sharedPreferences,
            gson = GsonBuilder().create()
        )
}
