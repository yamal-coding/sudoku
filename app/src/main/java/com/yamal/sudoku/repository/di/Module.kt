package com.yamal.sudoku.repository.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.yamal.sudoku.R
import com.yamal.sudoku.repository.BoardRepository
import com.yamal.sudoku.storage.BoardStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single { GsonBuilder().create() }

    single {
        val context = androidContext()
        context.getSharedPreferences(context.getString(R.string.shared_preferences_file_name), Context.MODE_PRIVATE)
    }

    single { BoardStorage(gson = get(), sharedPreferences = get()) }

    single {
        BoardRepository(
            boardStorage = get(),
            coroutineScope = CoroutineScope(SupervisorJob()),
            dispatchers = get()
        )
    }
}