package com.thoughtworks.carapp.domain.acmode

import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAcOnStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    operator fun invoke(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HVAC_AC_ON,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
            .map { it?.value as Boolean }
    }
}