package com.thoughtworks.carapp.device

import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.util.Log
import com.thoughtworks.carapp.presentation.main.MainViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class VehiclePropertyManager @Inject constructor(
    private val carPropertyManager: CarPropertyManager
) {

    fun getPropertyFlow(propId: Int, rate: Float): Flow<CarPropertyValue<*>?> = callbackFlow {
        val propertyCallback = object : CarPropertyManager.CarPropertyEventCallback {
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
            rate
        )

        awaitClose { carPropertyManager.unregisterCallback(propertyCallback) }
    }

    fun setBooleanProperty(propId: Int, areaId: Int, value: Boolean) {
        carPropertyManager.setBooleanProperty(propId, areaId, value)
    }
}
