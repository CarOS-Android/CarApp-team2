package com.thoughtworks.carapp.presentation.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.carapp.data.SampleDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SampleState {
    object Loading : SampleState()
    data class Success(val data: String) : SampleState()
}

sealed class SampleAction {
    object GetData : SampleAction()
}

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val dataSource: SampleDataSource
) : ViewModel() {

    private val _sampleState = MutableStateFlow<SampleState>(SampleState.Loading)
    val sampleState: StateFlow<SampleState>
        get() = _sampleState

    private fun getSampleData() {
        viewModelScope.launch {
            dataSource.getSampleData()
                .flowOn(Dispatchers.IO)
                .collect {
                    _sampleState.value = SampleState.Success(it)
                }
        }
    }

    fun sendAction(action: SampleAction) {
        when (action) {
            is SampleAction.GetData -> getSampleData()
        }
    }
}
