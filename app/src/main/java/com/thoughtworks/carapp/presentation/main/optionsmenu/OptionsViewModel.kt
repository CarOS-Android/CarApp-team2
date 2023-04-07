package com.thoughtworks.carapp.presentation.main.optionsmenu

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.thoughtworks.carapp.domain.GetWindowLockStatusUseCase
import com.thoughtworks.carapp.domain.SetWindowLockStatusUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface OptionsEvent : Event {

    data class PerformOptionClick(val option: Option) : OptionsEvent
}

@HiltViewModel
class OptionsViewModel @Inject constructor(
    getWindowLockStatus: GetWindowLockStatusUseCase,
    private val setWindowLockStatus: SetWindowLockStatusUseCase,
) : BaseViewModel() {
    val windowLockUiState: StateFlow<OptionUiState> = getWindowLockStatus()
        .map { OptionUiState(Option.WINDOW_LOCK, it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = OptionUiState(Option.WINDOW_LOCK, isActive = false, isEnable = true)
        )

    val fragranceUiState: StateFlow<OptionUiState> =
        MutableStateFlow(OptionUiState(Option.FRAGRANCE, isActive = false, isEnable = false))
            .asStateFlow()
    val wifiUiState: StateFlow<OptionUiState> =
        MutableStateFlow(OptionUiState(Option.WI_FI, isActive = false, isEnable = false))
            .asStateFlow()
    val bluetoothUiState: StateFlow<OptionUiState> =
        MutableStateFlow(OptionUiState(Option.BLUETOOTH, isActive = false, isEnable = false))
            .asStateFlow()
    val cellularUiState: StateFlow<OptionUiState> =
        MutableStateFlow(OptionUiState(Option.CELLULAR, isActive = false, isEnable = false))
            .asStateFlow()

    override fun handleEvents(event: Event) {
        when (event) {
            is OptionsEvent.PerformOptionClick -> onOptionClicked(event.option)
        }
    }

    private fun onOptionClicked(option: Option) {
        when (option) {
            Option.WINDOW_LOCK -> setWindowLockStatus(windowLockUiState.value.isActive.not())
            Option.FRAGRANCE,
            Option.WI_FI,
            Option.BLUETOOTH,
            Option.CELLULAR -> {
                Log.i(OptionsViewModel::class.simpleName, "Option $option is clicked!")
            }
        }
    }
}

data class OptionUiState(
    val option: Option,
    val isActive: Boolean = false,
    val isEnable: Boolean = true
)