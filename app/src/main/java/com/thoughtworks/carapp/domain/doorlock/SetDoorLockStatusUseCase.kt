package com.thoughtworks.carapp.domain.doorlock

import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.device.VehiclePropertyManager
import javax.inject.Inject

class SetDoorLockStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    operator fun invoke(value: Boolean) {
        SupportedDoorAreaIds.forEach { areaId ->
            vehiclePropertyManager.setProperty(
                VehiclePropertyIds.DOOR_LOCK, areaId, value
            )
        }
    }
}
