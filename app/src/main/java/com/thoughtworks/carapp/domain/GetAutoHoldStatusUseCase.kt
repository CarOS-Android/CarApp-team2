package com.thoughtworks.carapp.domain

import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.device.VehiclePropertyManager
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAutoHoldStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {

    operator fun invoke(): Flow<Boolean> {
        return vehiclePropertyManager.getProperty(VehiclePropertyIds.PARKING_BRAKE_AUTO_APPLY)
            .map { it?.value as Boolean }
    }
}
