package com.thoughtworks.carapp.domain

import android.car.VehicleAreaType
import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CarLightUseCase @Inject constructor(private val vehiclePropertyManager: VehiclePropertyManager) {
    fun hazardLightFlow(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HAZARD_LIGHTS_SWITCH,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        ).map { it?.value as Int }.map { it != LIGHT_OFF }
    }

    fun headLightFlow(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HEADLIGHTS_SWITCH,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        ).map { it?.value as Int }.map { it != LIGHT_OFF }
    }

    fun highBeamLightFlow(): Flow<Boolean> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HIGH_BEAM_LIGHTS_SWITCH,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        ).map { it?.value as Int }.map { it != LIGHT_OFF }
    }

    private fun changeLightState(propId: Int, state: Int) {
        vehiclePropertyManager.setProperty(
            propId,
            VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL,
            state
        )
    }

    fun turnOnHazardLight() {
        changeLightState(VehiclePropertyIds.HAZARD_LIGHTS_SWITCH, LIGHT_ON)
    }

    fun turnOffHazardLight() {
        changeLightState(VehiclePropertyIds.HAZARD_LIGHTS_SWITCH, LIGHT_OFF)
    }
    fun turnOnHeadLight() {
        changeLightState(VehiclePropertyIds.HEADLIGHTS_SWITCH, LIGHT_ON)
    }

    fun turnOffHeadLight() {
        changeLightState(VehiclePropertyIds.HEADLIGHTS_SWITCH, LIGHT_OFF)
    }

    companion object {
        const val LIGHT_ON = 1
        const val LIGHT_OFF = 0
    }
}