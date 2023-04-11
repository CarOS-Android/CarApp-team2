package com.thoughtworks.carapp.domain.acmode

import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.device.VehiclePropertyManager
import com.thoughtworks.carapp.domain.acmode.AcArea.ALL
import javax.inject.Inject

class SetAcOnStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    operator fun invoke(value: Boolean) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_AC_ON,
            ALL,
            value
        )
    }
}