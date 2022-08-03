package com.yamal.sudoku.game.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.Dialog

@Composable
fun GameFinished(
    onGoBackToMenu: () -> Unit,
) {
    Dialog(
        title = stringResource(id = R.string.game_finished_dialog_title),
        onDismissRequest = { },
        rightButtonText = stringResource(id = R.string.game_finished_dialog_go_to_menu_button),
        onRightButtonClick = onGoBackToMenu,
        leftButtonText = null,
        onLeftButtonClick = null
    )
}