package com.thoughtworks.carapp.domain.hvac

import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import com.thoughtworks.carapp.domain.model.HvacAreas
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AcOnStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    fun getAcOnStatusFlow(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HVAC_AC_ON,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
            .map { it?.value as Boolean }
    }

    fun setAcOnStatus(value: Boolean) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_AC_ON,
            HvacAreas.ALL,
            value
        )
    }
}