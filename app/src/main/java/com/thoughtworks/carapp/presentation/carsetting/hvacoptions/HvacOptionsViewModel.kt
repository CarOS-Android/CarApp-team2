package com.thoughtworks.carapp.presentation.carsetting.hvacoptions

import com.thoughtworks.carapp.domain.hvac.HvacOptionsUseCase
import com.thoughtworks.carapp.presentation.base.BaseViewModel
import com.thoughtworks.carapp.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

sealed interface HvacOptionsEvent : Event {
    data class OnHvacOptionSelected(val option: String) : HvacOptionsEvent
}

@HiltViewModel
class HvacOptionsViewModel @Inject constructor(
    private val hvacOptionsUseCase: HvacOptionsUseCase
) : BaseViewModel<HvacOptionsEvent>() {
    private val _frontDefrosterStatus = hvacOptionsUseCase.getFrontDefrosterStatusFlow()
        .map { OptionState(HvacOptions.FRONT_DEFROSTER, it) }
        .stateWith(OptionState(HvacOptions.FRONT_DEFROSTER))

    private val _rearDefrosterStatus = hvacOptionsUseCase.getRearDefrosterStatusFlow()
        .map { OptionState(HvacOptions.REAR_DEFROSTER, it) }
        .stateWith(OptionState(HvacOptions.REAR_DEFROSTER))

    private val _innerRecircStatus = hvacOptionsUseCase.getRecirculationStatusFlow()
        .map { OptionState(HvacOptions.INNER_RECIRCULATION, it) }
        .stateWith(OptionState(HvacOptions.INNER_RECIRCULATION))

    private val _outRecircStatus = hvacOptionsUseCase.getRecirculationStatusFlow()
        .map { OptionState(HvacOptions.OUT_RECIRCULATION, !it) }
        .stateWith(OptionState(HvacOptions.OUT_RECIRCULATION))

    private val _sideMirrorHeatStatus = hvacOptionsUseCase.getSideMirrorHeatStatusFlow()
        .map { OptionState(HvacOptions.SIDE_MIRROR_HEAT, it) }
        .stateWith(OptionState(HvacOptions.SIDE_MIRROR_HEAT))

    val uiState: StateFlow<Set<Int>> = combine(
        _frontDefrosterStatus,
        _rearDefrosterStatus,
        _innerRecircStatus,
        _outRecircStatus,
        _sideMirrorHeatStatus
    ) { frontDefroster, rearDefroster, innerRecirc, outRecirc, sideMirrorHeat ->
        renderUiState(frontDefroster, rearDefroster, innerRecirc, outRecirc, sideMirrorHeat)
    }
        .stateWith(emptySet())

    private fun renderUiState(vararg optionStates: OptionState): Set<Int> {
        return mutableSetOf<Int>().apply {
            optionStates.forEach { state ->
                if (state.isChecked) add(state.option.ordinal)
            }
        }
    }

    override fun handleEvents(event: HvacOptionsEvent) {
        when (event) {
            is HvacOptionsEvent.OnHvacOptionSelected -> handleHvacOptionSelected(event.option)
        }
    }

    private fun handleHvacOptionSelected(option: String) {
        val selectedOption = HvacOptions.values().find {
            it.name == option
        } ?: HvacOptions.PLACEHOLDER

        when (selectedOption) {
            HvacOptions.REAR_DEFROSTER ->
                hvacOptionsUseCase.setRearDefrosterStatus(!_rearDefrosterStatus.value.isChecked)
            HvacOptions.INNER_RECIRCULATION ->
                hvacOptionsUseCase.setRecirculationStatus(!_innerRecircStatus.value.isChecked)
            HvacOptions.OUT_RECIRCULATION ->
                hvacOptionsUseCase.setRecirculationStatus(_outRecircStatus.value.isChecked)
            HvacOptions.SIDE_MIRROR_HEAT ->
                hvacOptionsUseCase.setSideMirrorHeatStatus(!_sideMirrorHeatStatus.value.isChecked)
            HvacOptions.FRONT_DEFROSTER ->
                hvacOptionsUseCase.setFrontDefrosterStatus(!_frontDefrosterStatus.value.isChecked)
            HvacOptions.PLACEHOLDER -> {}
        }
    }

    data class OptionState(val option: HvacOptions, val isChecked: Boolean = false)
}