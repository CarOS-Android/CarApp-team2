package com.thoughtworks.carapp.domain

import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.device.VehicleAreaDoor
import com.thoughtworks.carapp.device.VehiclePropertyManager
import javax.inject.Inject

class SetDoorRearStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    operator fun invoke(value: Int) {
        vehiclePropertyManager.setIntProperty(
            propId = VehiclePropertyIds.DOOR_POS,
            areaId = VehicleAreaDoor.DOOR_REAR,
            value = value
        )
    }
}
