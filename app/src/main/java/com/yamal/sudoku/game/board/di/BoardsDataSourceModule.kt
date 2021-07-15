package com.yamal.sudoku.game.board.di

import com.yamal.sudoku.game.board.data.BoardsDataSource
import com.yamal.sudoku.game.board.data.BoardsDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BoardsDataSourceModule {
    @Provides
    fun provideBoardsDataSource(impl: BoardsDataSourceImpl): BoardsDataSource = impl
}
