package com.thoughtworks.carapp.presentation.main

import android.text.format.DateFormat
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.thoughtworks.carapp.domain.*
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MainScreenEvent : Event {
    object SwitchAutoHoldEvent : MainScreenEvent
    object EngineClickedEvent : MainScreenEvent
    object SwitchParkingBreakEvent : MainScreenEvent
    object SwitchDoorLockEvent : MainScreenEvent
    object SwitchDoorRearEvent : MainScreenEvent
}

@HiltViewModel
class MainViewModel @Inject constructor(
    getAutoHoldStatus: GetAutoHoldStatusUseCase,
    getEngineStatus: GetEngineStatusUseCase,
    getParkingBreakStatus: GetParkingBreakStatusUseCase,
    getDoorLockStatus: GetDoorLockStatusUseCase,
    getDoorRearStatus: GetDoorRearStatusUseCase,
    private val setDoorLockStatus: SetDoorLockStatusUseCase,
    private val setDoorRearStatus: SetDoorRearStatusUseCase
) : BaseViewModel() {
    val isAutoHoldOn: StateFlow<Boolean> = getAutoHoldStatus().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val isEngineStart: StateFlow<Boolean> = getEngineStatus().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val isDoorLockOn: StateFlow<Boolean> = getDoorLockStatus().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    val isParkingBreakOn: StateFlow<Boolean> = getParkingBreakStatus().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    val isDoorRearOn: StateFlow<Boolean> = getDoorRearStatus().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    val clockText = MutableStateFlow("")

    init {
        startClockTick()
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun startClockTick() {
        val ticker = ticker(TICK_INTERVAL, 0)
        viewModelScope.launch {
            for (event in ticker) {
                val currentTime = System.currentTimeMillis()
                clockText.value = DateFormat.format("HH:mm", currentTime).toString()
            }
        }
    }

    override fun handleEvents(event: Event) {
        when (event) {
            MainScreenEvent.SwitchAutoHoldEvent -> {
                Log.i(MainViewModel::class.simpleName, "Change Auto mode")
            }
            MainScreenEvent.EngineClickedEvent -> {
                if (isEngineStart.value.not()) {
                    Log.i(MainViewModel::class.simpleName, "Start engine")
                } else {
                    Log.i(MainViewModel::class.simpleName, "Stop engine")
                }
            }
            MainScreenEvent.SwitchParkingBreakEvent -> {
                Log.i(MainViewModel::class.simpleName, "Change Parking Break mode")
            }
            MainScreenEvent.SwitchDoorLockEvent -> {
                Log.i(MainViewModel::class.simpleName, "Change Door Lock mode")
                setDoorLockStatus(isDoorLockOn.value.not())
            }
            MainScreenEvent.SwitchDoorRearEvent -> {
                Log.i(MainViewModel::class.simpleName, "Change Door Rear mode")
                setDoorRearStatus(isDoorRearOn.value.not())
            }
        }
    }

    companion object {
        private const val TICK_INTERVAL = 1000L
    }
}
