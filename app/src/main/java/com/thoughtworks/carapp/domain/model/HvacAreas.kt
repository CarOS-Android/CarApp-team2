package com.thoughtworks.carapp.domain.model

import android.car.VehicleAreaMirror
import android.car.VehicleAreaSeat

object HvacAreas {

    const val LEFT = VehicleAreaSeat.SEAT_ROW_1_LEFT or
        VehicleAreaSeat.SEAT_ROW_2_LEFT or
        VehicleAreaSeat.SEAT_ROW_2_CENTER

    const val RIGHT = VehicleAreaSeat.SEAT_ROW_1_RIGHT or VehicleAreaSeat.SEAT_ROW_2_RIGHT

    const val ALL = LEFT or RIGHT

    const val SIDE_MIRROR_ALL = VehicleAreaMirror.MIRROR_DRIVER_LEFT or VehicleAreaMirror.MIRROR_DRIVER_RIGHT
}