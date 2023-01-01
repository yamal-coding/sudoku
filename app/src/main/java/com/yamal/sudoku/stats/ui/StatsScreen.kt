package com.yamal.sudoku.stats.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.MenuDivider
import com.yamal.sudoku.commons.ui.Title
import com.yamal.sudoku.commons.ui.animation.SlideInVerticalTransition
import com.yamal.sudoku.commons.ui.theme.SudokuTheme
import com.yamal.sudoku.stats.ui.viewmodel.GameStatisticsByDifficultyViewData
import com.yamal.sudoku.stats.ui.viewmodel.GameStatisticsViewData
import com.yamal.sudoku.stats.ui.viewmodel.StatisticsState
import com.yamal.sudoku.stats.ui.viewmodel.StatsViewModel

@Composable
fun StatsScreen(
    viewModel: StatsViewModel,
) {
    val state by viewModel.statisticsState.collectAsState(initial = StatisticsState.Idle)

    when (val statsState = state) {
        is StatisticsState.Idle -> {}
        is StatisticsState.Ready -> {
            SlideInVerticalTransition {
                StatisticsByDifficulty(
                    statisticsByDifficulty = statsState.statistics,
                )
            }
        }
    }
}

@Composable
private fun StatisticsByDifficulty(
    modifier: Modifier = Modifier,
    statisticsByDifficulty: GameStatisticsByDifficultyViewData
) {
    // TODO This design is temporary
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Title(
            textResId = R.string.statistics_screen_title
        )
        Statistics(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            titleResId = R.string.difficulty_easy,
            statistics = statisticsByDifficulty.easyStatistics,
        )
        MenuDivider()
        Statistics(
            modifier = Modifier.fillMaxWidth(),
            titleResId = R.string.difficulty_medium,
            statistics = statisticsByDifficulty.mediumStatistics,
        )
        MenuDivider()
        Statistics(
            modifier = Modifier.fillMaxWidth(),
            titleResId = R.string.difficulty_hard,
            statistics = statisticsByDifficulty.hardStatistics,
        )
    }
}

@Composable
private fun Statistics(
    modifier: Modifier = Modifier,
    @StringRes titleResId: Int,
    statistics: GameStatisticsViewData
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(text = stringResource(id = titleResId))
        StatisticsRow(
            modifier = Modifier.fillMaxWidth(),
            labelResId = R.string.statistics_best_time_label,
            value = statistics.bestTimeInSeconds ?: stringResource(id = R.string.statistics_best_time_undefined)
        )
        StatisticsRow(
            modifier = Modifier.fillMaxWidth(),
            labelResId = R.string.statistics_games_played_label,
            value = statistics.gamesPlayed.toString()
        )
        StatisticsRow(
            modifier = Modifier.fillMaxWidth(),
            labelResId = R.string.statistics_games_won_label,
            value = statistics.gamesWon.toString()
        )
    }
}

@Composable
private fun StatisticsRow(
    modifier: Modifier = Modifier,
    @StringRes labelResId: Int,
    value: String,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(id = labelResId)
        )
        Text(text = value)
    }
}

@Preview
@Composable
private fun StatisticsScreenPreview() {
    SudokuTheme {
        StatisticsByDifficulty(statisticsByDifficulty = GameStatisticsByDifficultyViewData(
            easyStatistics = GameStatisticsViewData("01:00", 1, 1),
            mediumStatistics = GameStatisticsViewData("01:00", 1, 1),
            hardStatistics = GameStatisticsViewData("01:00", 1, 1),
        ))
    }
}
