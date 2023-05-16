package com.thoughtworks.carapp.presentation.main.seats

import com.thoughtworks.carapp.domain.model.SeatArea
import com.thoughtworks.carapp.domain.model.SeatFuncGear
import com.thoughtworks.carapp.domain.seats.SeatBeltStatusUseCase
import com.thoughtworks.carapp.domain.seats.SeatStatusUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface SeatEvent : Event {
    object SwitchDriverSeatHeatingEvent : SeatEvent
    object SwitchCopilotSeatHeatingEvent : SeatEvent
    object SwitchDriverVentilationEvent : SeatEvent
    object SwitchCopilotVentilationEvent : SeatEvent
    object WearDriverSeatBeltEvent : SeatEvent
}

@HiltViewModel
class SeatViewModel @Inject constructor(
    private val seatStatusUseCase: SeatStatusUseCase,
    private val seatBeltStatusUseCase: SeatBeltStatusUseCase,
) : BaseViewModel<SeatEvent>() {
    val driverSeatHeatStatus = seatStatusUseCase
        .getSeatHeatStatusFlow(SeatArea.DRIVER_AREA)
        .stateWith(SeatFuncGear.GEAR_OFF)
    val copilotSeatHeatStatus = seatStatusUseCase
        .getSeatHeatStatusFlow(SeatArea.COPILOT_AREA)
        .stateWith(SeatFuncGear.GEAR_OFF)
    val driverSeatVentilationStatus = seatStatusUseCase
        .getSeatVentilationStatusFlow(SeatArea.DRIVER_AREA)
        .stateWith(SeatFuncGear.GEAR_OFF)
    val copilotSeatVentilationStatus = seatStatusUseCase
        .getSeatVentilationStatusFlow(SeatArea.COPILOT_AREA)
        .stateWith(SeatFuncGear.GEAR_OFF)
    val driverSeatBeltStatus = seatBeltStatusUseCase
        .driverSeatBeltStatusFlow()
        .stateWith(seatBeltStatusUseCase.getCurrentDriverSeatBeltStatus())

    override fun handleEvents(event: SeatEvent) {
        when (event) {
            SeatEvent.SwitchDriverSeatHeatingEvent -> {
                seatStatusUseCase.setSeatHeatStatus(
                    SeatArea.DRIVER_AREA,
                    driverSeatHeatStatus.value.getNextGear()
                )
            }
            SeatEvent.SwitchCopilotSeatHeatingEvent -> {
                seatStatusUseCase.setSeatHeatStatus(
                    SeatArea.COPILOT_AREA,
                    copilotSeatHeatStatus.value.getNextGear()
                )
            }
            SeatEvent.SwitchDriverVentilationEvent -> {
                seatStatusUseCase.setSeatVentilationStatus(
                    SeatArea.DRIVER_AREA,
                    driverSeatVentilationStatus.value.getNextGear()
                )
            }
            SeatEvent.SwitchCopilotVentilationEvent -> {
                seatStatusUseCase.setSeatVentilationStatus(
                    SeatArea.COPILOT_AREA,
                    copilotSeatVentilationStatus.value.getNextGear()
                )
            }
            SeatEvent.WearDriverSeatBeltEvent -> {
                seatBeltStatusUseCase.setSeatBeltStatus(
                    SeatArea.DRIVER_AREA,
                    true // todo
                )
            }
        }
    }
}
