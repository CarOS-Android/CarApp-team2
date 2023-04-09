package com.thoughtworks.carapp.presentation.main.optionsmenu

import android.util.Log
import com.thoughtworks.carapp.domain.windowlock.GetWindowLockStatusUseCase
import com.thoughtworks.carapp.domain.windowlock.SetWindowLockStatusUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

sealed interface OptionsEvent : Event {

    data class PerformOptionClick(val option: Option) : OptionsEvent
}

data class OptionsUiState(
    val options: List<OptionsViewModel.OptionState> = emptyList()
)

@HiltViewModel
class OptionsViewModel @Inject constructor(
    getWindowLockStatus: GetWindowLockStatusUseCase,
    private val setWindowLockStatus: SetWindowLockStatusUseCase,
) : BaseViewModel<OptionsEvent>() {
    private val _windowLockUiState: StateFlow<OptionState> = getWindowLockStatus()
        .map { OptionState(Option.WINDOW_LOCK, isActive = it, isEnable = true) }
        .stateWith(OptionState(Option.WINDOW_LOCK, isActive = false, isEnable = true))

    private val _fragranceUiState: MutableStateFlow<OptionState> = MutableStateFlow(OptionState(Option.FRAGRANCE))
    private val _wifiUiState: MutableStateFlow<OptionState> = MutableStateFlow(OptionState(Option.WI_FI))
    private val _bluetoothUiState: MutableStateFlow<OptionState> = MutableStateFlow(OptionState(Option.BLUETOOTH))
    private val _cellularUiState: MutableStateFlow<OptionState> = MutableStateFlow(OptionState(Option.CELLULAR))

    val uiState: StateFlow<OptionsUiState> = combine(
        _windowLockUiState, _fragranceUiState, _wifiUiState, _bluetoothUiState, _cellularUiState
    ) { windowLock, fragrance, wifi, bluetooth, cellular ->
        OptionsUiState(listOf(windowLock, fragrance, wifi, bluetooth, cellular))
    }.stateWith(OptionsUiState())

    override fun handleEvents(event: OptionsEvent) {
        when (event) {
            is OptionsEvent.PerformOptionClick -> onOptionClicked(event.option)
        }
    }

    private fun onOptionClicked(option: Option) {
        when (option) {
            Option.WINDOW_LOCK -> setWindowLockStatus(_windowLockUiState.value.isActive.not())
            Option.FRAGRANCE,
            Option.WI_FI,
            Option.BLUETOOTH,
            Option.CELLULAR -> {
                Log.i(OptionsViewModel::class.simpleName, "Option $option is clicked!")
            }
        }
    }

    data class OptionState(
        val option: Option,
        val isActive: Boolean = false,
        val isEnable: Boolean = false
    )
}