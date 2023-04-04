package com.thoughtworks.carapp.presentation.main

import android.car.VehiclePropertyIds
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.thoughtworks.carapp.domain.GetAutoHoldUserCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

sealed interface MainScreenEvent : Event {
    object SwitchAutoHoldMode : MainScreenEvent
}

@HiltViewModel
class MainViewModel @Inject constructor(
    getAutoHoldUserCase: GetAutoHoldUserCase
) : BaseViewModel() {

    val isAutoHoldOn: StateFlow<Boolean> = getAutoHoldUserCase(
        VehiclePropertyIds.PARKING_BRAKE_AUTO_APPLY
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    override fun handleEvents(event: Event) {
        when (event) {
            MainScreenEvent.SwitchAutoHoldMode -> {
                Log.i(MainViewModel::class.simpleName, "Change Auto mode")
            }
        }
    }
}
