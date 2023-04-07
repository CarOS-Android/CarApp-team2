package com.thoughtworks.carapp.domain

import android.car.VehicleAreaWindow
import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.device.VehiclePropertyManager
import javax.inject.Inject

class SetWindowLockStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {

    operator fun invoke(value: Boolean) {
        vehiclePropertyManager.setBooleanProperty(
            propId = VehiclePropertyIds.WINDOW_LOCK,
            areaId = VehicleAreaWindow.WINDOW_ROW_1_RIGHT
                or VehicleAreaWindow.WINDOW_ROW_2_LEFT
                or VehicleAreaWindow.WINDOW_ROW_2_RIGHT,
            value = value
        )
    }
}