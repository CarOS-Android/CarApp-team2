package com.thoughtworks.carapp.domain.doorlock

import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDoorLockStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    private val doorLockedMap = mutableMapOf<Int, Boolean>()

    operator fun invoke(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.DOOR_LOCK,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
            .filter { it?.areaId in SupportedDoorAreaIds }
            .map {
                it?.let {
                    doorLockedMap[it.areaId] = it.value as Boolean
                    doorLockedMap.values.all { item -> item }
                } ?: false
            }
    }
}