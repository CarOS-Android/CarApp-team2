package com.thoughtworks.carapp.domain.seats

import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import com.thoughtworks.carapp.domain.model.SeatArea
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SeatBeltStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {

    fun getCurrentDriverSeatBeltStatus(): Boolean {
        return getCurrentSeatBeltStatus(SeatArea.DRIVER_AREA)
    }

    fun getCurrentCopilotSeatBeltStatus(): Boolean {
        return getCurrentSeatBeltStatus(SeatArea.COPILOT_AREA)
    }

    fun setSeatBeltStatus(areaId: Int, value: Boolean) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.SEAT_BELT_BUCKLED,
            areaId,
            value
        )
    }

    fun driverSeatBeltStatusFlow(): Flow<Boolean> {
        return seatBeltStatusFlow(SeatArea.DRIVER_AREA)
    }

    fun copilotSeatBeltStatusFlow(): Flow<Boolean> {
        return seatBeltStatusFlow(SeatArea.COPILOT_AREA)
    }

    private fun getCurrentSeatBeltStatus(areaId: Int): Boolean {
        return vehiclePropertyManager.getProperty(
            VehiclePropertyIds.SEAT_BELT_BUCKLED,
            areaId
        )
    }

    private fun seatBeltStatusFlow(areaId: Int): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.SEAT_BELT_BUCKLED,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        ).filterNotNull().filter { it.areaId == areaId }.map { it.value as Boolean }
    }

}