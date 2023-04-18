package com.thoughtworks.carapp.domain.hvac

import android.car.VehicleAreaWindow
import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HvacOptionsUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {

    fun getFrontDefrosterStatusFlow(): Flow<Boolean> {
        return getDefrosterStatus(VehicleAreaWindow.WINDOW_FRONT_WINDSHIELD)
    }

    fun setFrontDefrosterStatus(value: Boolean) {
        val areaId = VehicleAreaWindow.WINDOW_FRONT_WINDSHIELD
        setDefrosterStatus(areaId, value)
    }

    fun getRearDefrosterStatusFlow(): Flow<Boolean> {
        return getDefrosterStatus(VehicleAreaWindow.WINDOW_REAR_WINDSHIELD)
    }

    fun setRearDefrosterStatus(value: Boolean) {
        val areaId = VehicleAreaWindow.WINDOW_REAR_WINDSHIELD
        setDefrosterStatus(areaId, value)
    }

    fun getRecirculationStatusFlow(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HVAC_RECIRC_ON,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        ).map { it?.value as Boolean }
    }

    fun setRecirculationStatus(value: Boolean) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_RECIRC_ON,
            HvacAreas.ALL,
            value
        )
    }

    fun getSideMirrorHeatStatusFlow(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HVAC_SIDE_MIRROR_HEAT,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
            .map { it?.value as Int }
            .map { it != SIDE_MIRROR_HEAT_OFF }
    }

    fun setSideMirrorHeatStatus(value: Boolean) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_SIDE_MIRROR_HEAT,
            HvacAreas.SIDE_MIRROR_ALL,
            if (value) SIDE_MIRROR_HEAT_ON else SIDE_MIRROR_HEAT_OFF
        )
    }

    private fun setDefrosterStatus(areaId: Int, value: Boolean) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_DEFROSTER,
            areaId,
            value
        )
    }

    private fun getDefrosterStatus(area: Int) = vehiclePropertyManager.getPropertyFlow(
        VehiclePropertyIds.HVAC_DEFROSTER,
        CarPropertyManager.SENSOR_RATE_ONCHANGE
    )
        .filterNotNull()
        .filter { it.areaId == area }
        .map { it.value as Boolean }

    companion object {

        private const val SIDE_MIRROR_HEAT_ON = 1
        private const val SIDE_MIRROR_HEAT_OFF = 0
    }
}
