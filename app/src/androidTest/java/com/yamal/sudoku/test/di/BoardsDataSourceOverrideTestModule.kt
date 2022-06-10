package com.yamal.sudoku.test.di

import com.yamal.sudoku.game.level.data.datasource.LevelsDataSource
import com.yamal.sudoku.game.level.di.LevelsDataSourceModule
import com.yamal.sudoku.test.mocks.FakeLevelsDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [LevelsDataSourceModule::class]
)
object BoardsDataSourceOverrideTestModule {
    @Provides
    fun provideBoardsDataSource(impl: FakeLevelsDataSourceImpl): LevelsDataSource = impl
}
