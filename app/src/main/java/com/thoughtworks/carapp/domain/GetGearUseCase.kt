package com.thoughtworks.carapp.domain

import android.car.VehicleGear
import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGearUseCase @Inject constructor(private val vehiclePropertyManager: VehiclePropertyManager) {
    operator fun invoke(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.CURRENT_GEAR,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        ).map { it?.value as Int }.map { it == VehicleGear.GEAR_PARK }
    }
}