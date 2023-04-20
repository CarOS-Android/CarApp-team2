package com.thoughtworks.carapp.domain.fragrance

import android.car.VehicleAreaSeat
import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import com.thoughtworks.carapp.presentation.carsetting.fragrance.FragranceOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FragranceUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {

    fun getDriverAreaStatus(): Flow<FragranceOptions> {
        return getFragranceStatus(VehicleAreaSeat.SEAT_ROW_1_LEFT)
    }

    fun getCopilotAreaStatus(): Flow<FragranceOptions> {
        return getFragranceStatus(VehicleAreaSeat.SEAT_ROW_1_RIGHT)
    }

    fun getBackSeatAreaStatus(): Flow<FragranceOptions> {
        return getFragranceStatus(VehicleAreaSeat.SEAT_ROW_2_CENTER)
    }

    private fun getFragranceStatus(areaId: Int) = vehiclePropertyManager
        .getPropertyFlow(VehiclePropertyIds.HVAC_FRAGRANCE, CarPropertyManager.SENSOR_RATE_ONCHANGE)
        .filterNotNull()
        .filter { it.areaId == areaId }
        .map { it.value as Int }
        .map { status ->
            when (status) {
                1 -> FragranceOptions.SECRET
                2 -> FragranceOptions.STAR
                3 -> FragranceOptions.SUNSHINE
                else -> FragranceOptions.CLOSED
            }
        }

    fun setDriverStatus(option: FragranceOptions) {
        setFragranceStatus(option, VehicleAreaSeat.SEAT_ROW_1_LEFT)
    }

    fun setCopilotStatus(option: FragranceOptions) {
        setFragranceStatus(option, VehicleAreaSeat.SEAT_ROW_1_RIGHT)
    }

    fun setBackSeatStatus(option: FragranceOptions) {
        setFragranceStatus(option, VehicleAreaSeat.SEAT_ROW_2_CENTER)
    }

    private fun setFragranceStatus(
        option: FragranceOptions,
        areaId: Int
    ) {
        val value = when (option) {
            FragranceOptions.STAR -> 2
            FragranceOptions.SUNSHINE -> 3
            FragranceOptions.SECRET -> 1
            FragranceOptions.CLOSED -> 0
        }
        vehiclePropertyManager.setProperty(
            VehiclePropertyIds.HVAC_FRAGRANCE,
            areaId,
            value
        )
    }
}