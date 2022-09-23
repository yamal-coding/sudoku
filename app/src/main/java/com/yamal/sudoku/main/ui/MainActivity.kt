package com.yamal.sudoku.main.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import com.yamal.sudoku.commons.ui.theme.SudokuTheme
import com.yamal.sudoku.navigation.SudokuNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SudokuTheme {
                Scaffold {
                    SudokuNavHost()
                }
            }
        }
    }
}
