package com.thoughtworks.carapp.presentation.carsetting

import com.thoughtworks.carapp.domain.acmode.GetAcPowerStatusUseCase
import com.thoughtworks.carapp.domain.acmode.SetAcPowerStatusUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

sealed interface SettingScreenEvent : Event {
    object SwitchAcPowerEvent : SettingScreenEvent
}

@HiltViewModel
class SettingViewModel @Inject constructor(
    getAcPowerStatus: GetAcPowerStatusUseCase,
    private val setAcPowerStatus: SetAcPowerStatusUseCase,
) : BaseViewModel<SettingScreenEvent>() {
    val isAcPowerOn: StateFlow<Boolean> = getAcPowerStatus().stateWith(false)

    override fun handleEvents(event: SettingScreenEvent) {
        when (event) {
            SettingScreenEvent.SwitchAcPowerEvent -> {
                setAcPowerStatus(isAcPowerOn.value.not())
            }
        }
    }
}