package com.thoughtworks.carapp.domain.seats

import android.car.VehicleAreaSeat
import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.device.VehiclePropertyManager
import javax.inject.Inject

class SetSeatHeatingStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    operator fun invoke(value: Int) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_SEAT_TEMPERATURE,
            VehicleAreaSeat.SEAT_ROW_1_LEFT,
            value
        )
    }
}
