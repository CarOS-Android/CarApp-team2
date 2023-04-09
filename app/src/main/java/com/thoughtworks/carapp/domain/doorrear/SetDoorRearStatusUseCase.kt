package com.thoughtworks.carapp.domain.doorrear

import android.car.VehicleAreaDoor
import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.device.VehiclePropertyManager
import javax.inject.Inject

class SetDoorRearStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    operator fun invoke(value: Boolean) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.DOOR_POS,
            VehicleAreaDoor.DOOR_REAR,
            if (value) DoorRearStatus.CLOSE else DoorRearStatus.OPEN
        )
    }
}
