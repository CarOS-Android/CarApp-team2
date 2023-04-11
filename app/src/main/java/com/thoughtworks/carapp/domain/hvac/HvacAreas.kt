package com.thoughtworks.carapp.domain.hvac

import android.car.VehicleAreaSeat

object HvacAreas {

    const val LEFT = VehicleAreaSeat.SEAT_ROW_1_LEFT or
        VehicleAreaSeat.SEAT_ROW_2_LEFT or
        VehicleAreaSeat.SEAT_ROW_2_CENTER

    const val RIGHT = VehicleAreaSeat.SEAT_ROW_1_RIGHT or VehicleAreaSeat.SEAT_ROW_2_RIGHT

    const val ALL = LEFT or RIGHT
}