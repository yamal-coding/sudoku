package com.yamal.sudoku.test.di

import com.yamal.sudoku.game.board.data.BoardsDataSource
import com.yamal.sudoku.game.board.di.BoardsDataSourceModule
import com.yamal.sudoku.test.mocks.FakeBoardsDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [BoardsDataSourceModule::class]
)
object BoardsDataSourceOverrideTestModule {
    @Provides
    fun provideBoardsDataSource(impl: FakeBoardsDataSourceImpl): BoardsDataSource = impl
}
