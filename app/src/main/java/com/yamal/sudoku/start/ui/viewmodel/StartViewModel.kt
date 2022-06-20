package com.yamal.sudoku.start.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.yamal.sudoku.start.domain.HasSavedBoard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val hasSavedBoard: HasSavedBoard,
) : ViewModel() {

    val shouldShowContinueButton: Flow<Boolean>
        get() = hasSavedBoard.observe()
}