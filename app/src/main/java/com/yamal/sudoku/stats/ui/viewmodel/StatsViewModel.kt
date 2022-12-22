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
    private val viewModelMapper: StatisticsViewModelMapper,
) : ViewModel() {
    val statisticsState: Flow<StatisticsState> =
        getGameStatistics().map {
            StatisticsState.Ready(statistics = viewModelMapper.toViewModel(it))
        }
}

sealed class StatisticsState {
    object Idle : StatisticsState()
    data class Ready(
        val statistics: GameStatisticsByDifficultyViewData
    ) : StatisticsState()
}
