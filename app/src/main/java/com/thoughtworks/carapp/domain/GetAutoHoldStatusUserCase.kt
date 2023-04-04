package com.thoughtworks.carapp.domain

import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.device.VehiclePropertyManager
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAutoHoldStatusUserCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {

    operator fun invoke(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(VehiclePropertyIds.PARKING_BRAKE_AUTO_APPLY)
            .map { it?.value as Boolean }
    }
}
