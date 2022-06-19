package com.yamal.sudoku.main.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.yamal.sudoku.main.domain.HasSavedBoard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Suppress("UNUSED")
@HiltViewModel
class MainComposeViewModel @Inject constructor(
    private val hasSavedBoard: HasSavedBoard,
) : ViewModel() {

    val shouldShowContinueButton: Flow<Boolean>
        get() = hasSavedBoard.observe()
}