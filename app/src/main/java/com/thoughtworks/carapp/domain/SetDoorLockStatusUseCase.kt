package com.thoughtworks.carapp.domain

import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.device.VehicleAreaDoor
import com.thoughtworks.carapp.device.VehiclePropertyManager
import javax.inject.Inject

class SetDoorLockStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    operator fun invoke(value: Boolean) {
        vehiclePropertyManager.setBooleanProperty(
            propId = VehiclePropertyIds.DOOR_LOCK,
            areaId = VehicleAreaDoor.DOOR_ROW_1_LEFT,
            value = value
        )
        vehiclePropertyManager.setBooleanProperty(
            propId = VehiclePropertyIds.DOOR_LOCK,
            areaId = VehicleAreaDoor.DOOR_ROW_1_RIGHT,
            value = value
        )
        vehiclePropertyManager.setBooleanProperty(
            propId = VehiclePropertyIds.DOOR_LOCK,
            areaId = VehicleAreaDoor.DOOR_ROW_2_LEFT,
            value = value
        )
        vehiclePropertyManager.setBooleanProperty(
            propId = VehiclePropertyIds.DOOR_LOCK,
            areaId = VehicleAreaDoor.DOOR_ROW_2_RIGHT,
            value = value
        )
    }
}
