package com.thoughtworks.carapp.domain

import com.thoughtworks.carapp.device.VehiclePropertyManager
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAutoHoldUserCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {

    operator fun invoke(propId: Int): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(propId)
            .map { it?.value as Boolean }
    }
}
