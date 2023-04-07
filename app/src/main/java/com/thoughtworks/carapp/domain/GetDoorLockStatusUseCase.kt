package com.thoughtworks.carapp.domain

import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehicleAreaDoor
import com.thoughtworks.carapp.device.VehiclePropertyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDoorLockStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    private var doorLockedMap = mutableMapOf(
        VehicleAreaDoor.DOOR_ROW_1_LEFT to true,
        VehicleAreaDoor.DOOR_ROW_1_RIGHT to true,
        VehicleAreaDoor.DOOR_ROW_2_LEFT to true,
        VehicleAreaDoor.DOOR_ROW_2_RIGHT to true
    )
    operator fun invoke(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.DOOR_LOCK,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        ).map {
            it?.let {
                doorLockedMap[it.areaId] = it.value as Boolean
                doorLockedMap.values.all { item -> item }
            } ?: false
        }
    }
}