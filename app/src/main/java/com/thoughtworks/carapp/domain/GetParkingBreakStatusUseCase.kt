package com.thoughtworks.carapp.domain

import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.device.VehiclePropertyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetParkingBreakStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    operator fun invoke(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(VehiclePropertyIds.PARKING_BRAKE_ON)
            .map { it?.value as Boolean }
    }
}