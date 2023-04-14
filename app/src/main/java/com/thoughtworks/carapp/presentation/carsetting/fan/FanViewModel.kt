package com.thoughtworks.carapp.presentation.carsetting.fan
import android.car.VehicleHvacFanDirection
import com.thoughtworks.carapp.domain.FanDirectionUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface FanEvent : Event {
    class SetFanDirection(val direction: Int) : FanEvent
}

@HiltViewModel
class FanViewModel @Inject constructor(
    private val fanDirectionUseCase: FanDirectionUseCase
) : BaseViewModel<FanEvent>() {
    val fanDirection = fanDirectionUseCase.fanFlow().stateWith(VehicleHvacFanDirection.FACE)
    override fun handleEvents(event: FanEvent) {
        when (event) {
            is FanEvent.SetFanDirection -> fanDirectionUseCase.setFanDirection(event.direction)
        }
    }
}
