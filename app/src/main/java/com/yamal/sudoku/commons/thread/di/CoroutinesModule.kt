package com.yamal.sudoku.commons.thread.di

import com.yamal.sudoku.commons.thread.CoroutineDispatcherProvider
import com.yamal.sudoku.commons.thread.CoroutineDispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {
    @Provides
    fun provideCoroutineDispatcherProvider(impl: CoroutineDispatcherProviderImpl): CoroutineDispatcherProvider = impl
}