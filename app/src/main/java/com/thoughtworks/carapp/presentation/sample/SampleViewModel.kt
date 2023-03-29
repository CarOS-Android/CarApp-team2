package com.thoughtworks.carapp.presentation.sample

import androidx.lifecycle.viewModelScope
import com.thoughtworks.carapp.data.SampleDataSource
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SampleState {
    object Loading : SampleState
    data class Success(val data: String) : SampleState
}

sealed interface ThumpUpState {
    object Liked : ThumpUpState
    object Unliked : ThumpUpState
}

sealed interface SampleEvent : Event {
    object LoadDataEvent : SampleEvent
    object ThumbsUpEvent : SampleEvent
}

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val dataSource: SampleDataSource
) : BaseViewModel() {

    val sampleState: StateFlow<SampleState> = dataSource.getSampleData().map {
        SampleState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SampleState.Loading,
    )

    private val _thumpUpState = MutableStateFlow<ThumpUpState>(ThumpUpState.Unliked)
    val thumpUpState: StateFlow<ThumpUpState> = _thumpUpState.asStateFlow()

    fun thumpUp() {
        viewModelScope.launch {
            dataSource.thumpUp((_thumpUpState.value is ThumpUpState.Liked)).collect { result ->
                _thumpUpState.value = if (result) ThumpUpState.Liked else ThumpUpState.Unliked
            }
        }
    }

    // --------------------------------111111111111111-----------------------------------

    private val _thumpUpState2 = MutableStateFlow<ThumpUpState>(ThumpUpState.Unliked)
    val thumpUpState2: StateFlow<ThumpUpState> = _thumpUpState2.asStateFlow()

    override fun handleEvents(event: Event) {
        when (event) {
            is SampleEvent.LoadDataEvent -> loadData()
            is SampleEvent.ThumbsUpEvent -> thumpUp((_thumpUpState.value is ThumpUpState.Liked))
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            // ....
        }
    }

    private fun thumpUp(isLiked: Boolean) {
        viewModelScope.launch {
            dataSource.thumpUp(isLiked).collect { result ->
                _thumpUpState.value = if (result) ThumpUpState.Liked else ThumpUpState.Unliked
            }
        }
    }
}
