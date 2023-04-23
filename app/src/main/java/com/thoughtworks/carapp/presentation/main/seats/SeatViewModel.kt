package com.thoughtworks.carapp.presentation.main.seats

import com.thoughtworks.carapp.domain.model.SeatArea
import com.thoughtworks.carapp.domain.seats.SeatStatusUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface SeatEvent : Event {
    object SwitchDriverSeatHeatingEvent : SeatEvent
    object SwitchCopilotSeatHeatingEvent : SeatEvent
}

@HiltViewModel
class SeatViewModel @Inject constructor(
    private val seatStatusUseCase: SeatStatusUseCase
) : BaseViewModel<SeatEvent>() {
    val driverSeatHeatStatus = seatStatusUseCase
        .getSeatHeatStatusFlow(SeatArea.DRIVER_AREA)
        .stateWith(0)
    val copilotSeatHeatStatus = seatStatusUseCase
        .getSeatHeatStatusFlow(SeatArea.COPILOT_AREA)
        .stateWith(0)

    override fun handleEvents(event: SeatEvent) {
        when (event) {
            SeatEvent.SwitchDriverSeatHeatingEvent -> {
                seatStatusUseCase.setSeatHeatStatus(
                    SeatArea.DRIVER_AREA,
                    if (driverSeatHeatStatus.value == 2) 0 else driverSeatHeatStatus.value + 1
                )
            }
            SeatEvent.SwitchCopilotSeatHeatingEvent -> {
                seatStatusUseCase.setSeatHeatStatus(
                    SeatArea.COPILOT_AREA,
                    if (copilotSeatHeatStatus.value == 2) 0 else copilotSeatHeatStatus.value + 1
                )
            }
        }
    }
}
