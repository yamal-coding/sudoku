package com.yamal.sudoku.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import com.yamal.sudoku.commons.ui.theme.SudokuTheme
import com.yamal.sudoku.navigation.SudokuNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SudokuTheme() {
                Scaffold(
                    modifier = Modifier.safeDrawingPadding()
                ) { insets ->
                    SudokuNavHost(
                        modifier = Modifier.padding(insets),
                    )
                }
            }
        }
    }
}
