package com.thoughtworks.carapp.presentation.main.doors

import com.thoughtworks.carapp.domain.doorlock.GetDoorLockStatusUseCase
import com.thoughtworks.carapp.domain.doorlock.SetDoorLockStatusUseCase
import com.thoughtworks.carapp.domain.doorrear.GetDoorRearStatusUseCase
import com.thoughtworks.carapp.domain.doorrear.SetDoorRearStatusUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface DoorsEvent : Event {
    object SwitchDoorLockEvent : DoorsEvent
    object SwitchDoorRearEvent : DoorsEvent
}

@HiltViewModel
class DoorsViewModel @Inject constructor(
    getDoorLockStatus: GetDoorLockStatusUseCase,
    getDoorRearStatus: GetDoorRearStatusUseCase,
    private val setDoorLockStatus: SetDoorLockStatusUseCase,
    private val setDoorRearStatus: SetDoorRearStatusUseCase
) : BaseViewModel<DoorsEvent>() {
    val isDoorsLocked = getDoorLockStatus().stateWith(true)
    val isDoorRearLocked = getDoorRearStatus().stateWith(true)

    override fun handleEvents(event: DoorsEvent) {
        when (event) {
            DoorsEvent.SwitchDoorLockEvent -> {
                setDoorLockStatus(isDoorsLocked.value.not())
            }
            DoorsEvent.SwitchDoorRearEvent -> {
                setDoorRearStatus(isDoorRearLocked.value.not())
            }
        }
    }
}