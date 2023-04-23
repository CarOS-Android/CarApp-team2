package com.thoughtworks.carapp.presentation.carsetting.fragrance

import com.thoughtworks.carapp.domain.fragrance.FragranceUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface FragranceEvent : Event {
    data class DriverFragranceEvent(val fragranceLabel: String) : FragranceEvent
    data class CopilotFragranceEvent(val fragranceLabel: String) : FragranceEvent
    data class BackSeatFragranceEvent(val fragranceLabel: String) : FragranceEvent
    object SetFragranceOpenStatus : FragranceEvent
}

@HiltViewModel
class FragranceViewModel @Inject constructor(
    private val fragranceUseCase: FragranceUseCase
) : BaseViewModel<FragranceEvent>() {
    val driverState = fragranceUseCase.getDriverAreaStatus()
        .stateWith(FragranceOptions.SECRET)
    val copilotState = fragranceUseCase.getCopilotAreaStatus()
        .stateWith(FragranceOptions.SECRET)
    val backSeatState = fragranceUseCase.getBackSeatAreaStatus()
        .stateWith(FragranceOptions.SECRET)
    val isFragranceOn = fragranceUseCase.fragranceOpenState

    override fun handleEvents(event: FragranceEvent) {
        when (event) {
            is FragranceEvent.BackSeatFragranceEvent -> handleBackSeatFragranceEvent(event.fragranceLabel)
            is FragranceEvent.CopilotFragranceEvent -> handleCopilotFragranceEvent(event.fragranceLabel)
            is FragranceEvent.DriverFragranceEvent -> handleDriverFragranceEvent(event.fragranceLabel)
            FragranceEvent.SetFragranceOpenStatus -> updateFragranceOpenStatus()
        }
    }

    private fun updateFragranceOpenStatus() {
        fragranceUseCase.setWholeAreaOpenStatus(isFragranceOn.value.not())
    }

    private fun handleDriverFragranceEvent(label: String) {
        val selectedOption = FragranceOptions.getOptionByName(label)
        fragranceUseCase.setDriverStatus(selectedOption)
    }

    private fun handleCopilotFragranceEvent(label: String) {
        val selectedOption = FragranceOptions.getOptionByName(label)
        fragranceUseCase.setCopilotStatus(selectedOption)
    }

    private fun handleBackSeatFragranceEvent(label: String) {
        val selectedOption = FragranceOptions.getOptionByName(label)
        fragranceUseCase.setBackSeatStatus(selectedOption)
    }
}