package com.thoughtworks.carapp.presentation.carsetting.ac

import com.thoughtworks.carapp.domain.acmode.GetAcAutoStatusUseCase
import com.thoughtworks.carapp.domain.acmode.GetAcPowerStatusUseCase
import com.thoughtworks.carapp.domain.acmode.SetAcAutoStatusUseCase
import com.thoughtworks.carapp.domain.acmode.SetAcPowerStatusUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

sealed interface SettingScreenEvent : Event {
    object SwitchAcPowerEvent : SettingScreenEvent
    object SwitchAcAutoEvent : SettingScreenEvent
}

@HiltViewModel
class AcViewModel @Inject constructor(
    getAcPowerStatus: GetAcPowerStatusUseCase,
    getAcAutoStatus: GetAcAutoStatusUseCase,
    private val setAcPowerStatus: SetAcPowerStatusUseCase,
    private val setAcAutoStatus: SetAcAutoStatusUseCase,
) : BaseViewModel<SettingScreenEvent>() {
    val isAcPowerOn: StateFlow<Boolean> = getAcPowerStatus().stateWith(false)
    val isAcAutoOn: StateFlow<Boolean> = getAcAutoStatus().stateWith(false)

    override fun handleEvents(event: SettingScreenEvent) {
        when (event) {
            SettingScreenEvent.SwitchAcPowerEvent -> {
                setAcPowerStatus(isAcPowerOn.value.not())
            }
            SettingScreenEvent.SwitchAcAutoEvent -> {
                setAcAutoStatus(isAcAutoOn.value.not())
            }
        }
    }
}