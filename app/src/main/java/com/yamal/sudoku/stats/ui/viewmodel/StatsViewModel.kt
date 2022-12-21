package com.yamal.sudoku.stats.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.yamal.sudoku.stats.domain.GetGameStatistics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    getGameStatistics: GetGameStatistics,
) : ViewModel() {
    val statisticsState: Flow<StatisticsState> =
        getGameStatistics().map {
            StatisticsState.Ready(statistics = it.toViewData())
        }
}

sealed class StatisticsState {
    object Idle : StatisticsState()
    data class Ready(
        val statistics: GameStatisticsByDifficultyViewData
    ) : StatisticsState()
}
