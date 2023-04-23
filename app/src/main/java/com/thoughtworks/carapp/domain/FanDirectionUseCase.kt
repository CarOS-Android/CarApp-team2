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
        val faceDirection = FanDirection.values()[direction - 1]

        vehiclePropertyManager.setProperty(
            propId = VehiclePropertyIds.HVAC_FAN_DIRECTION,
            areaId = HvacAreas.ALL,
            state = faceDirection.value
        )
    }

    fun getFanDirectionFlow(): Flow<FanDirection> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HVAC_FAN_DIRECTION,
            CarPropertyManager.SENSOR_RATE_ONCHANGE,
        )
            .filterNotNull()
            .map { it.value as Int }
            .map { direction ->
                FanDirection.values().find { it.value == direction } ?: FanDirection.FACE
            }
    }
}

enum class FanDirection(val text: String, val value: Int) {
    FACE("面部", VehicleHvacFanDirection.FACE),
    FLOOR("脚部", VehicleHvacFanDirection.FLOOR),
    DEFROST("除霜", VehicleHvacFanDirection.DEFROST);
}