package com.thoughtworks.carapp.domain.windowlock

import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.device.VehiclePropertyManager
import javax.inject.Inject

class SetWindowLockStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {

    operator fun invoke(value: Boolean) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.WINDOW_LOCK,
            SupportedWindowAreaIds.reduce { acc, i -> acc or i },
            value
        )
    }
}