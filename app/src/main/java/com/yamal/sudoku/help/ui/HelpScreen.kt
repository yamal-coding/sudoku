package com.yamal.sudoku.help.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.Paragraph
import com.yamal.sudoku.commons.ui.Title
import com.yamal.sudoku.commons.ui.animation.SlideInVerticalTransition
import com.yamal.sudoku.commons.ui.theme.SudokuTheme

@Composable
fun HelpScreen() {
    SlideInVerticalTransition {
        HelpScreenImpl()
    }
}

@Composable
private fun HelpScreenImpl() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp),
    ) {
        Title(
            textResId = R.string.how_to_play_title
        )
        Paragraph(
            modifier = Modifier.padding(top = 20.dp),
            textResId = R.string.how_to_play_paragraph_1
        )
        BulletPoint(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            textResId = R.string.how_to_play_condition_1
        )
        BulletPoint(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            textResId = R.string.how_to_play_condition_2
        )
        BulletPoint(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            textResId = R.string.how_to_play_condition_3
        )
        Paragraph(
            textResId = R.string.how_to_play_paragraph_2,
        )
    }
}

@Composable
private fun BulletPoint(
    modifier: Modifier = Modifier,
    @StringRes textResId: Int,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = null
        )
        Paragraph(
            modifier = Modifier.padding(start = 12.dp),
            textResId = textResId,
        )
    }
}

@Preview
@Composable
fun HelpScreenPreview() {
    SudokuTheme {
        HelpScreen()
    }
}