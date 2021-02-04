package com.yamal.sudoku.commons.thread.di

import com.yamal.sudoku.commons.thread.JobDispatcher
import com.yamal.sudoku.commons.thread.JobDispatcherImpl
import org.koin.dsl.module

val threadingModule = module {
    single<JobDispatcher> { JobDispatcherImpl() }
}
