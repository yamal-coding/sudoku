package com.yamal.sudoku.game.level.di

import com.yamal.sudoku.game.level.data.LevelsDataSource
import com.yamal.sudoku.game.level.data.LevelsDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LevelsDataSourceModule {
    @Provides
    fun provideLevelsDataSource(impl: LevelsDataSourceImpl): LevelsDataSource = impl
}