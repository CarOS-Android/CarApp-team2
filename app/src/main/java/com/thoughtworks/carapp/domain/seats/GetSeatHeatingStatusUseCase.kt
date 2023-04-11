package com.thoughtworks.carapp.domain.seats

import android.car.VehicleAreaSeat
import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSeatHeatingStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    operator fun invoke(): Flow<Int> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HVAC_SEAT_TEMPERATURE,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
            .filter { it?.areaId == VehicleAreaSeat.SEAT_ROW_1_LEFT }
            .map { it?.value as Int }
    }
}
