package com.yamal.sudoku.commons.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes textRes: Int,
    enabled: Boolean = true,
    @DrawableRes icon: Int? = null,
    testTag: String? = null,
) {
    val buttonModifier = modifier.fillMaxWidth()
    val withTextTagModifier = testTag?.let {
        buttonModifier.then(Modifier.testTag(it))
    } ?: buttonModifier

    TextButton(
        modifier = withTextTagModifier,
        onClick = onClick,
        enabled = enabled,
    ) {
        icon?.let {
            Icon(
                modifier = Modifier.padding(horizontal = 8.dp),
                painter = painterResource(id = it),
                contentDescription = null
            )
        }
        Text(
            text = stringResource(id = textRes).uppercase(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Composable
fun MenuDivider(
    modifier: Modifier = Modifier
) {
    Divider(modifier = modifier.fillMaxWidth())
}
