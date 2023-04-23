package com.thoughtworks.carapp.domain.seats

import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import com.thoughtworks.carapp.domain.model.SeatFuncGear
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SeatStatusUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    fun getSeatHeatStatusFlow(areaId: Int): Flow<SeatFuncGear> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HVAC_SEAT_TEMPERATURE,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
            .filter { it?.areaId == areaId }
            .map { propertyValue ->
                SeatFuncGear.values().find {
                    it.value == propertyValue?.value
                } ?: SeatFuncGear.GEAR_OFF
            }
    }

    fun getSeatVentilationStatusFlow(areaId: Int): Flow<SeatFuncGear> {
        return vehiclePropertyManager.getPropertyFlow(
            VehiclePropertyIds.HVAC_SEAT_VENTILATION,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
            .filter { it?.areaId == areaId }
            .map { propertyValue ->
                SeatFuncGear.values().find {
                    it.value == propertyValue?.value
                } ?: SeatFuncGear.GEAR_OFF
            }
    }

    fun setSeatHeatStatus(areaId: Int, value: SeatFuncGear) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_SEAT_TEMPERATURE,
            areaId,
            value.value
        )
    }

    fun setSeatVentilationStatus(areaId: Int, value: SeatFuncGear) {
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_SEAT_VENTILATION,
            areaId,
            value.value
        )
    }
}
