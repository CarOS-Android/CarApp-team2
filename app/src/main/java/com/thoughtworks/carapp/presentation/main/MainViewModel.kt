package com.thoughtworks.carapp.presentation.main

import android.text.format.DateFormat
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.thoughtworks.carapp.domain.GetEngineStatusUseCase
import com.thoughtworks.carapp.domain.GetGearUseCase
import com.thoughtworks.carapp.domain.GetParkingBreakStatusUseCase
import com.thoughtworks.carapp.domain.autohold.GetAutoHoldStatusUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MainScreenEvent : Event {
    object SwitchAutoHoldEvent : MainScreenEvent
    object EngineClickedEvent : MainScreenEvent
    object SwitchParkingBreakEvent : MainScreenEvent
}

@HiltViewModel
class MainViewModel @Inject constructor(
    getAutoHoldStatus: GetAutoHoldStatusUseCase,
    getEngineStatus: GetEngineStatusUseCase,
    getParkingBreakStatus: GetParkingBreakStatusUseCase,
    getGearUseCase: GetGearUseCase
) : BaseViewModel<MainScreenEvent>() {
    val isAutoHoldOn: StateFlow<Boolean> = getAutoHoldStatus().stateWith(false)
    val isEngineStart: StateFlow<Boolean> = getEngineStatus().stateWith(false)
    val isParkingBreakOn: StateFlow<Boolean> = getParkingBreakStatus().stateWith(true)
    val isParking = getGearUseCase().stateWith(true)

    private val _clockText = MutableStateFlow("")
    val clockText: StateFlow<String> = _clockText.asStateFlow()

    init {
        startClockTick()
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun startClockTick() {
        val ticker = ticker(TICK_INTERVAL, 0)
        viewModelScope.launch {
            for (event in ticker) {
                val currentTime = System.currentTimeMillis()
                _clockText.value = DateFormat.format("HH:mm", currentTime).toString()
            }
        }
    }

    override fun handleEvents(event: MainScreenEvent) {
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
        }
    }

    companion object {
        private const val TICK_INTERVAL = 1000L
    }
}
