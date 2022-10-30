package com.yamal.sudoku.game.level.di

import android.content.Context
import androidx.room.Room
import com.yamal.sudoku.game.level.data.datasource.LevelsDataSource
import com.yamal.sudoku.game.level.data.datasource.LevelsDataSourceImpl
import com.yamal.sudoku.game.level.data.datasource.db.LevelsDao
import com.yamal.sudoku.game.level.data.datasource.db.LevelsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LevelsDataSourceModule {
    @Provides
    fun provideLevelsDataSource(impl: LevelsDataSourceImpl): LevelsDataSource = impl

    @Singleton
    @Provides
    fun provideLevelsDatabase(@ApplicationContext context: Context): LevelsDatabase =
        Room.databaseBuilder(
            context,
            LevelsDatabase::class.java, "levels-database"
        ).build()

    @Provides
    fun provideLevelsDao(database: LevelsDatabase): LevelsDao =
        database.levelsDao()
}
