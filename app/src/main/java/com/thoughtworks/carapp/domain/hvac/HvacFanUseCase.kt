package com.thoughtworks.carapp.domain.hvac

import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import com.thoughtworks.carapp.domain.model.FanSpeed
import com.thoughtworks.carapp.domain.model.HvacAreas
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HvacFanUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    fun setFanSpeed(speed: Int) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_FAN_SPEED,
            HvacAreas.ALL,
            speed
        )
    }

    fun getFanSpeedFlow(): Flow<FanSpeed> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HVAC_FAN_SPEED,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
            .filterNotNull()
            .map { it.value as Int }
            .map { speed ->
                FanSpeed.values().find { it.value == speed }
                    ?: when {
                        speed > FanSpeed.SPEED_MAX.value -> FanSpeed.SPEED_MAX
                        else -> FanSpeed.OFF
                    }
            }
    }
}