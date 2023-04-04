package com.thoughtworks.carapp.presentation.main

import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.car.hardware.property.CarPropertyManager.CarPropertyEventCallback
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private const val PropertyDeliverRate = 10F

sealed interface MainScreenEvent : Event {
    object SwitchAutoHoldMode : MainScreenEvent
    object SwitchParkingBreakMode : MainScreenEvent
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val carPropertyManager: CarPropertyManager
) : BaseViewModel() {

    val autoHoldUiState: StateFlow<Boolean> = getPropertyFlow(
        VehiclePropertyIds.PARKING_BRAKE_AUTO_APPLY
    ).map { it?.value as Boolean }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val parkingBreakUiState: StateFlow<Boolean> = getPropertyFlow(
        VehiclePropertyIds.PARKING_BRAKE_ON
    ).map { it?.value as Boolean }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    override fun handleEvents(event: Event) {
        when (event) {
            MainScreenEvent.SwitchAutoHoldMode -> {
                Log.i(MainViewModel::class.simpleName, "Change Auto mode")
            }
            MainScreenEvent.SwitchParkingBreakMode -> {
                Log.i(MainViewModel::class.simpleName, "Change Parking Break mode")
            }
        }
    }

    private fun getPropertyFlow(
        propId: Int
    ): Flow<CarPropertyValue<*>?> = callbackFlow {
        val propertyCallback = object : CarPropertyEventCallback {
            override fun onChangeEvent(value: CarPropertyValue<*>?) {
                trySend(value)
            }

            override fun onErrorEvent(propId: Int, zone: Int) {
                Log.e(
                    MainViewModel::javaClass.name,
                    "Property event error: PropertyId:$propId, zone: $zone"
                )
                error("Property event error: PropertyId:$propId, zone: $zone")
            }
        }

        carPropertyManager.registerCallback(
            propertyCallback,
            propId,
            PropertyDeliverRate
        )

        awaitClose { carPropertyManager.unregisterCallback(propertyCallback) }
    }
}
