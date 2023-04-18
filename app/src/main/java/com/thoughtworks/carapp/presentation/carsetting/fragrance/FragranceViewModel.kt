package com.thoughtworks.carapp.presentation.carsetting.fragrance

import android.util.Log
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

sealed interface FragranceEvent : Event {
    data class DriverFragranceEvent(val fragranceLabel: String) : FragranceEvent
    data class CopilotFragranceEvent(val fragranceLabel: String) : FragranceEvent
    data class BackSeatFragranceEvent(val fragranceLabel: String) : FragranceEvent
}

@HiltViewModel
class FragranceViewModel @Inject constructor() : BaseViewModel<FragranceEvent>() {
    val driverState = emptyFlow<FragranceOptions>()
        .stateWith(FragranceOptions.SECRET)
    val copilotState = emptyFlow<FragranceOptions>()
        .stateWith(FragranceOptions.STAR)
    val backSeatState = emptyFlow<FragranceOptions>()
        .stateWith(FragranceOptions.SUNSHINE)

    override fun handleEvents(event: FragranceEvent) {
        when (event) {
            is FragranceEvent.BackSeatFragranceEvent -> handleBackSeatFragranceEvent(event.fragranceLabel)
            is FragranceEvent.CopilotFragranceEvent -> handleCopilotFragranceEvent(event.fragranceLabel)
            is FragranceEvent.DriverFragranceEvent -> handleDriverFragranceEvent(event.fragranceLabel)
        }
    }

    private fun handleDriverFragranceEvent(label: String) {
        val selectedOption = FragranceOptions.getOptionByName(label)
        Log.i("FragranceViewModel", "handleDriverFragranceEvent: $selectedOption")
    }

    private fun handleCopilotFragranceEvent(label: String) {
        val selectedOption = FragranceOptions.getOptionByName(label)
        Log.i("FragranceViewModel", "handleCopilotFragranceEvent: $selectedOption")
    }

    private fun handleBackSeatFragranceEvent(label: String) {
        val selectedOption = FragranceOptions.getOptionByName(label)
        Log.i("FragranceViewModel", "handleBackSeatFragranceEvent: $selectedOption")
    }
}