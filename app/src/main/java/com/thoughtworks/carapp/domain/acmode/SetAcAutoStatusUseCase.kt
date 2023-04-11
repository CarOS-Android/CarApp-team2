package com.thoughtworks.carapp.domain.acmode

import android.car.VehicleAreaSeat
import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.device.VehiclePropertyManager
import javax.inject.Inject

class SetAcAutoStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    operator fun invoke(value: Boolean) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_AUTO_ON,
            ALL,
            value
        )
    }

    companion object {
        private const val LEFT =
            VehicleAreaSeat.SEAT_ROW_1_LEFT or VehicleAreaSeat.SEAT_ROW_2_LEFT or VehicleAreaSeat.SEAT_ROW_2_CENTER

        private const val RIGHT =
            VehicleAreaSeat.SEAT_ROW_1_RIGHT or VehicleAreaSeat.SEAT_ROW_2_RIGHT

        private const val ALL = LEFT or RIGHT
    }
}