package com.thoughtworks.carapp.domain

import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehicleAreaDoor
import com.thoughtworks.carapp.device.VehiclePropertyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDoorRearStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    operator fun invoke(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.DOOR_POS,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
            .filter { it?.areaId == VehicleAreaDoor.DOOR_REAR }
            .map { it?.value as Int == 0 }
    }
}
