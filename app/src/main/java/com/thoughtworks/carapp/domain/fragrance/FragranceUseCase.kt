package com.thoughtworks.carapp.domain.fragrance

import android.car.FragranceState
import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import com.thoughtworks.carapp.device.VehiclePropertyManager
import com.thoughtworks.carapp.domain.model.SeatArea
import com.thoughtworks.carapp.presentation.carsetting.fragrance.FragranceOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FragranceUseCase @Inject constructor(
    private val vehiclePropertyManager: VehiclePropertyManager
) {
    private val _fragranceOpenState = MutableStateFlow(false)
    val fragranceOpenState: StateFlow<Boolean> = _fragranceOpenState.asStateFlow()

    fun getDriverAreaStatus(): Flow<FragranceOptions> {
        return getAreaFragranceStatus(SeatArea.DRIVER_AREA)
    }

    fun getCopilotAreaStatus(): Flow<FragranceOptions> {
        return getAreaFragranceStatus(SeatArea.COPILOT_AREA)
    }

    fun getBackSeatAreaStatus(): Flow<FragranceOptions> {
        return getAreaFragranceStatus(SeatArea.BACK_SEAT_AREA)
    }

    fun setWholeAreaOpenStatus(isOpen: Boolean) {
        _fragranceOpenState.update { isOpen }
    }

    private fun getAreaFragranceStatus(areaId: Int): Flow<FragranceOptions> {
        return vehiclePropertyManager
            .getPropertyFlow(VehiclePropertyIds.HVAC_FRAGRANCE, CarPropertyManager.SENSOR_RATE_ONCHANGE)
            .filterNotNull()
            .filter { it.areaId == areaId }
            .map { it.value as Int }
            .map { status ->
                when (status) {
                    FragranceState.SECRET -> FragranceOptions.SECRET
                    FragranceState.STAR -> FragranceOptions.STAR
                    FragranceState.SUNSHINE -> FragranceOptions.SUNSHINE
                    else -> FragranceOptions.CLOSED
                }
            }
            .onEach { newState ->
                _fragranceOpenState.update { newState != FragranceOptions.CLOSED }
            }
            .filterNot { it == FragranceOptions.CLOSED }
    }

    fun setDriverStatus(option: FragranceOptions) {
        setFragranceStatus(option, SeatArea.DRIVER_AREA)
    }

    fun setCopilotStatus(option: FragranceOptions) {
        setFragranceStatus(option, SeatArea.COPILOT_AREA)
    }

    fun setBackSeatStatus(option: FragranceOptions) {
        setFragranceStatus(option, SeatArea.BACK_SEAT_AREA)
    }

    private fun setFragranceStatus(
        option: FragranceOptions,
        areaId: Int
    ) {
        val value = when (option) {
            FragranceOptions.CLOSED -> FragranceState.CLOSED
            FragranceOptions.SECRET -> FragranceState.SECRET
            FragranceOptions.STAR -> FragranceState.STAR
            FragranceOptions.SUNSHINE -> FragranceState.SUNSHINE
        }
        vehiclePropertyManager.setProperty(VehiclePropertyIds.HVAC_FRAGRANCE, areaId, value)
    }
}
