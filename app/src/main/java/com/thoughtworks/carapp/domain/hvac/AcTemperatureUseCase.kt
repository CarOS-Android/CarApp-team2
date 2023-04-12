package com.thoughtworks.carapp.domain.hvac

import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AcTemperatureUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    fun getCurrentLeftTemperature(): Int {
        return getCurrentTemperature(HvacAreas.LEFT)
    }

    fun getCurrentRightTemperature(): Int {
        return getCurrentTemperature(HvacAreas.RIGHT)
    }

    fun leftTemperatureFlow(): Flow<Int> {
        return temperatureFlow(HvacAreas.LEFT).map { it.toInt() }
    }

    fun rightTemperatureFlow(): Flow<Int> {
        return temperatureFlow(HvacAreas.RIGHT).map { it.toInt() }
    }

    private fun temperatureFlow(side: Int): Flow<Float> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HVAC_TEMPERATURE_SET,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        ).filterNotNull().filter { it.areaId == side }.map { it.value as Float }
    }

    private fun getCurrentTemperature(side: Int): Int {
        return vehiclePropertyManager.getProperty<Float>(VehiclePropertyIds.HVAC_TEMPERATURE_SET, side).toInt()
    }

    fun setRightTemperature(temperature: Int) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_TEMPERATURE_SET,
            HvacAreas.RIGHT,
            temperature.toFloat()
        )
    }

    fun setLeftTemperature(temperature: Int) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_TEMPERATURE_SET,
            HvacAreas.LEFT,
            temperature.toFloat()
        )
    }
}