package com.thoughtworks.carapp.domain

import android.car.VehicleHvacFanDirection
import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import com.thoughtworks.carapp.domain.hvac.HvacAreas
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FanDirectionUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    fun setFanDirection(direction: Int) {
        val value = when (direction) {
            1 -> VehicleHvacFanDirection.FACE
            2 -> VehicleHvacFanDirection.FLOOR
            else -> VehicleHvacFanDirection.DEFROST
        }
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_FAN_DIRECTION, HvacAreas.ALL, value
        )
    }
    fun fanFlow(): Flow<Int> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HVAC_FAN_DIRECTION,
            CarPropertyManager.SENSOR_RATE_ONCHANGE,
        ).filterNotNull().map { it.value as Int }.map { direction ->
            when (direction) {
                VehicleHvacFanDirection.FACE -> 1
                VehicleHvacFanDirection.FLOOR -> 2
                else -> 3
            }
        }
    }
}

