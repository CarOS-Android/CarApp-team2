package com.thoughtworks.carapp.presentation.main.hvac

import com.thoughtworks.carapp.domain.hvac.AcTemperatureUseCase
import com.thoughtworks.carapp.domain.hvac.HvacFanUseCase
import com.thoughtworks.carapp.domain.model.FanSpeed
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface AcEvent : Event {
    class SetLeftSideTemperature(val temperature: Int) : AcEvent
    class SetRightSideTemperature(val temperature: Int) : AcEvent
    class SetFanSpeed(val speed: Int) : AcEvent
}

@HiltViewModel
class AcViewModel @Inject constructor(
    private val acTemperatureUseCase: AcTemperatureUseCase,
    private val hvacFanUseCase: HvacFanUseCase
) : BaseViewModel<AcEvent>() {
    val leftTemperature = acTemperatureUseCase.leftTemperatureFlow()
        .stateWith(acTemperatureUseCase.getCurrentLeftTemperature())
    val rightTemperature = acTemperatureUseCase.rightTemperatureFlow()
        .stateWith(acTemperatureUseCase.getCurrentRightTemperature())
    val fanSpeed = hvacFanUseCase.getFanSpeedFlow()
        .stateWith(FanSpeed.OFF)

    override fun handleEvents(event: AcEvent) {
        when (event) {
            is AcEvent.SetLeftSideTemperature -> acTemperatureUseCase.setLeftTemperature(event.temperature)
            is AcEvent.SetRightSideTemperature -> acTemperatureUseCase.setRightTemperature(event.temperature)
            is AcEvent.SetFanSpeed -> hvacFanUseCase.setFanSpeed(event.speed)
        }
    }
}