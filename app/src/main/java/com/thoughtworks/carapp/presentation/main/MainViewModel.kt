package com.thoughtworks.carapp.presentation.main

import android.text.format.DateFormat
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.thoughtworks.carapp.domain.CarLightUseCase
import com.thoughtworks.carapp.domain.GetEngineStatusUseCase
import com.thoughtworks.carapp.domain.GetGearUseCase
import com.thoughtworks.carapp.domain.GetParkingBreakStatusUseCase
import com.thoughtworks.carapp.domain.autohold.GetAutoHoldStatusUseCase
import com.thoughtworks.carapp.domain.doorlock.GetDoorLockStatusUseCase
import com.thoughtworks.carapp.domain.doorlock.SetDoorLockStatusUseCase
import com.thoughtworks.carapp.domain.doorrear.GetDoorRearStatusUseCase
import com.thoughtworks.carapp.domain.doorrear.SetDoorRearStatusUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.Flow
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
    object HazardLightEvent : MainScreenEvent
    object HeadLightEvent : MainScreenEvent
    object HighBeamLightEvent : MainScreenEvent
}

@HiltViewModel
class MainViewModel @Inject constructor(
    getAutoHoldStatus: GetAutoHoldStatusUseCase,
    getEngineStatus: GetEngineStatusUseCase,
    getParkingBreakStatus: GetParkingBreakStatusUseCase,
    getDoorLockStatus: GetDoorLockStatusUseCase,
    getDoorRearStatus: GetDoorRearStatusUseCase,
    getGearUseCase: GetGearUseCase,
    private val carLightUseCase: CarLightUseCase,
    private val setDoorLockStatus: SetDoorLockStatusUseCase,
    private val setDoorRearStatus: SetDoorRearStatusUseCase
) : BaseViewModel() {
    val isAutoHoldOn: StateFlow<Boolean> = getAutoHoldStatus().stateWith(false)
    val isEngineStart: StateFlow<Boolean> = getEngineStatus().stateWith(false)
    val isParkingBreakOn: StateFlow<Boolean> = getParkingBreakStatus().stateWith(true)
    val isHazardLightOn = carLightUseCase.hazardLightFlow().stateWith(false)
    val isHeadLightOn = carLightUseCase.headLightFlow().stateWith(false)
    val isHighBeamLightOn = carLightUseCase.highBeamLightFlow().stateWith(false)
    val isParking = getGearUseCase().stateWith(true)
    val isDoorLockOn = getDoorLockStatus().stateWith(true)
    val isDoorRearLocked = getDoorRearStatus().stateWith(true)

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
                setDoorRearStatus(isDoorRearLocked.value.not())
            }
            MainScreenEvent.HazardLightEvent -> {
                if (isHazardLightOn.value) {
                    carLightUseCase.turnOffHazardLight()
                } else {
                    carLightUseCase.turnOnHazardLight()
                }
            }
            MainScreenEvent.HeadLightEvent -> {
                if (isHeadLightOn.value) {
                    carLightUseCase.turnOffHeadLight()
                } else {
                    carLightUseCase.turnOnHeadLight()
                    if (isHighBeamLightOn.value) {
                        carLightUseCase.turnOffHighBeamLight()
                    }
                }
            }
            MainScreenEvent.HighBeamLightEvent -> {
                if (isHighBeamLightOn.value) {
                    carLightUseCase.turnOffHighBeamLight()
                } else {
                    carLightUseCase.turnOnHighBeamLight()
                    if (isHeadLightOn.value) {
                        carLightUseCase.turnOffHeadLight()
                    }
                }
            }
        }
    }

    private fun <T> Flow<T>.stateWith(initValue: T): StateFlow<T> {
        return stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIME_OUT),
            initialValue = initValue
        )
    }

    companion object {
        private const val STOP_TIME_OUT = 5000L
        private const val TICK_INTERVAL = 1000L
    }
}
