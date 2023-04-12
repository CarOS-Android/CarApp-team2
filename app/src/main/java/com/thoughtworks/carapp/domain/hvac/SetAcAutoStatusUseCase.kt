package com.thoughtworks.carapp.domain.hvac

import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.device.VehiclePropertyManager
import javax.inject.Inject

class SetAcAutoStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    operator fun invoke(value: Boolean) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_AUTO_ON,
            HvacAreas.ALL,
            value
        )
    }
}