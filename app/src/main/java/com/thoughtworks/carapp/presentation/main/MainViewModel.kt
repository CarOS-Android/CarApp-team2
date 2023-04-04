package com.thoughtworks.carapp.presentation.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.thoughtworks.carapp.domain.GetAutoHoldStatusUserCase
import com.thoughtworks.carapp.domain.GetEngineStatusUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

sealed interface MainScreenEvent : Event {
    object SwitchAutoHoldModeEvent : MainScreenEvent
    object StartEngineEvent : MainScreenEvent
    object StopEngineEvent : MainScreenEvent
}

@HiltViewModel
class MainViewModel @Inject constructor(
    getAutoHoldStatusUserCase: GetAutoHoldStatusUserCase,
    getEngineStatusUseCase: GetEngineStatusUseCase
) : BaseViewModel() {

    val isAutoHoldOn: StateFlow<Boolean> = getAutoHoldStatusUserCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val isEngineOn: StateFlow<Boolean> = getEngineStatusUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    override fun handleEvents(event: Event) {
        when (event) {
            MainScreenEvent.SwitchAutoHoldModeEvent -> {
                Log.i(MainViewModel::class.simpleName, "Change Auto mode")
            }
            MainScreenEvent.StartEngineEvent -> {
                Log.i(MainViewModel::class.simpleName, "Start engine")
            }
            MainScreenEvent.StopEngineEvent -> {
                Log.i(MainViewModel::class.simpleName, "Stop engine")
            }
        }
    }
}
