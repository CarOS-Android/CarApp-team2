package com.thoughtworks.carapp.presentation.main.hvac

import com.thoughtworks.carapp.domain.AcTemperatureUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface AcEvent : Event {
    class SetTemperature(val side: Int, val temperature: Int) : AcEvent
}

@HiltViewModel
class AcViewModel @Inject constructor(
    private val acTemperatureUseCase: AcTemperatureUseCase
) : BaseViewModel<Event>() {
    val leftTemperature = acTemperatureUseCase.leftTemperatureFlow()
        .stateWith(acTemperatureUseCase.getCurrentLeftTemperature())
    val rightTemperature = acTemperatureUseCase.rightTemperatureFlow()
        .stateWith(acTemperatureUseCase.getCurrentRightTemperature())

    override fun handleEvents(event: Event) {
        if (event is AcEvent.SetTemperature) {
            acTemperatureUseCase.setCurrentTemperature(event.side, event.temperature)
        }
    }
}