package com.yamal.sudoku.commons.thread.di

import com.yamal.sudoku.commons.thread.CoroutineDispatcherProvider
import com.yamal.sudoku.commons.thread.CoroutineDispatcherProviderImpl
import org.koin.dsl.module

val threadingModule = module {
    single<CoroutineDispatcherProvider> { CoroutineDispatcherProviderImpl() }
}
