package com.thoughtworks.carapp.presentation.carsetting.ac

import com.thoughtworks.carapp.domain.acmode.GetAcOnStatusUseCase
import com.thoughtworks.carapp.domain.acmode.SetAcOnStatusUseCase
import com.thoughtworks.carapp.domain.hvac.AcPowerStatusUseCase
import com.thoughtworks.carapp.domain.hvac.GetAcAutoStatusUseCase
import com.thoughtworks.carapp.domain.hvac.SetAcAutoStatusUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

sealed interface SettingScreenEvent : Event {
    object SwitchAcPowerEvent : SettingScreenEvent
    object SwitchAcAutoEvent : SettingScreenEvent
    object SwitchAcOnEvent : SettingScreenEvent
}

@HiltViewModel
class AcViewModel @Inject constructor(
    private val acPowerStatus: AcPowerStatusUseCase,
    getAcAutoStatus: GetAcAutoStatusUseCase,
    getAcOnStatus: GetAcOnStatusUseCase,
    private val setAcAutoStatus: SetAcAutoStatusUseCase,
    private val setAcOnStatus: SetAcOnStatusUseCase,
    ) : BaseViewModel<SettingScreenEvent>() {
    val isAcPowerOn: StateFlow<Boolean> = acPowerStatus.getAcPowerStatusFlow().stateWith(false)
    val isAcAutoOn: StateFlow<Boolean> = getAcAutoStatus().stateWith(false)
    val isAcOn: StateFlow<Boolean> = getAcOnStatus().stateWith(false)

    override fun handleEvents(event: SettingScreenEvent) {
        when (event) {
            SettingScreenEvent.SwitchAcPowerEvent -> {
                acPowerStatus.setAcPowerStatus(isAcPowerOn.value.not())
            }
            SettingScreenEvent.SwitchAcAutoEvent -> {
                setAcAutoStatus(isAcAutoOn.value.not())
            }
            SettingScreenEvent.SwitchAcOnEvent -> {
                if (isAcPowerOn.value) {
                    setAcOnStatus(isAcOn.value.not())
                }
            }
        }
    }
}