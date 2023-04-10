package com.thoughtworks.carapp.presentation.main.carlights

import com.thoughtworks.carapp.domain.CarLightUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

sealed interface CarLightEvent : Event {
    object HazardLightEvent : CarLightEvent
    object HeadLightEvent : CarLightEvent
    object HighBeamLightEvent : CarLightEvent
}

@HiltViewModel
class CarLightViewModel @Inject constructor(
    private val carLightUseCase: CarLightUseCase,
) : BaseViewModel<CarLightEvent>() {
    val isHazardLightOn = carLightUseCase.hazardLightFlow().stateWith(false)
    val isHeadLightOn = carLightUseCase.headLightFlow()
        .onEach { if (it) carLightUseCase.turnOffHighBeamLight() }
        .stateWith(false)
    val isHighBeamLightOn = carLightUseCase.highBeamLightFlow()
        .onEach { if (it) carLightUseCase.turnOffHeadLight() }
        .stateWith(false)

    override fun handleEvents(event: CarLightEvent) {
        when (event) {
            CarLightEvent.HazardLightEvent -> {
                if (isHazardLightOn.value) {
                    carLightUseCase.turnOffHazardLight()
                } else {
                    carLightUseCase.turnOnHazardLight()
                }
            }
            CarLightEvent.HeadLightEvent -> {
                if (isHeadLightOn.value) {
                    carLightUseCase.turnOffHeadLight()
                } else {
                    carLightUseCase.turnOnHeadLight()
                    if (isHighBeamLightOn.value) {
                        carLightUseCase.turnOffHighBeamLight()
                    }
                }
            }
            CarLightEvent.HighBeamLightEvent -> {
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
}