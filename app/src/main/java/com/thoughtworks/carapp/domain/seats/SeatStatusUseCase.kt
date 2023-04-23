package com.thoughtworks.carapp.domain.seats

import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SeatStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    fun getSeatHeatStatusFlow(areaId: Int): Flow<Int> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HVAC_SEAT_TEMPERATURE,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
            .filter { it?.areaId == areaId }
            .map { it?.value as Int }
    }

    fun setSeatHeatStatus(areaId: Int, value: Int) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_SEAT_TEMPERATURE,
            areaId,
            value
        )
    }
}
