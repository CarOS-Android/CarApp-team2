package com.thoughtworks.carapp.presentation.main.seats

import com.thoughtworks.carapp.domain.seats.GetSeatHeatingStatusUseCase
import com.thoughtworks.carapp.domain.seats.SetSeatHeatingStatusUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface SeatEvent : Event {
    object SwitchSeatHeatingEvent : SeatEvent
}

@HiltViewModel
class SeatViewModel @Inject constructor(
    getSeatHeatingStatus: GetSeatHeatingStatusUseCase,
    private val setSeatHeatingStatus: SetSeatHeatingStatusUseCase
) : BaseViewModel<SeatEvent>() {
    val isSeatHeatingOpened = getSeatHeatingStatus().stateWith(0)

    override fun handleEvents(event: SeatEvent) {
        when (event) {
            SeatEvent.SwitchSeatHeatingEvent -> {
                setSeatHeatingStatus(
                    if (isSeatHeatingOpened.value == 2) 0 else isSeatHeatingOpened.value + 1
                )
            }
        }
    }
}
