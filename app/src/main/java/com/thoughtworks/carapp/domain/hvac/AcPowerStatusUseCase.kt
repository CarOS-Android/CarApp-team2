package com.thoughtworks.carapp.domain.hvac

import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AcPowerStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    fun getAcPowerStatusFlow(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HVAC_POWER_ON,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
            .map { it?.value as Boolean }
    }

    fun setAcPowerStatus(value: Boolean) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_POWER_ON,
            HvacAreas.ALL,
            value
        )
    }
}