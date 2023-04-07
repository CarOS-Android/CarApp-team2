package com.thoughtworks.carapp.presentation.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.thoughtworks.carapp.domain.*
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface MainScreenEvent : Event {
    object SwitchAutoHoldModeEvent : MainScreenEvent
    object StartEngineEvent : MainScreenEvent
    object StopEngineEvent : MainScreenEvent
    object SwitchDoorLockEvent : MainScreenEvent
    object SwitchDoorRearEvent : MainScreenEvent
}

@HiltViewModel
class MainViewModel @Inject constructor(
    getAutoHoldStatusUseCase: GetAutoHoldStatusUseCase,
    getEngineStatusUseCase: GetEngineStatusUseCase,
    getDoorLockStatusUseCase: GetDoorLockStatusUseCase,
    getDoorRearStatusUseCase: GetDoorRearStatusUseCase,
    private val setDoorLockStatusUseCase: SetDoorLockStatusUseCase,
    private val setDoorRearStatusUseCase: SetDoorRearStatusUseCase
) : BaseViewModel() {

    val isAutoHoldOn: StateFlow<Boolean> = getAutoHoldStatusUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val isEngineOn: StateFlow<Boolean> = getEngineStatusUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val isDoorLockOn: StateFlow<Boolean> = getDoorLockStatusUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    val isDoorRearOn: StateFlow<Int> = getDoorRearStatusUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
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
            MainScreenEvent.SwitchDoorLockEvent -> {
                Log.i(MainViewModel::class.simpleName, "Change Door Lock mode")
                setDoorLockStatusUseCase(isDoorLockOn.value.not())
            }
            MainScreenEvent.SwitchDoorRearEvent -> {
                Log.i(MainViewModel::class.simpleName, "Change Door Rear mode")
                setDoorRearStatusUseCase(if (isDoorRearOn.value == 0) 1 else 0)
            }
        }
    }
}
