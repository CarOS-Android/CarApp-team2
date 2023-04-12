package com.thoughtworks.carapp.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

interface Event

abstract class BaseViewModel<E : Event> : ViewModel() {

    private val _event: MutableSharedFlow<E> = MutableSharedFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    fun sendEvent(event: E) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun <T> Flow<T>.stateWith(initValue: T): StateFlow<T> {
        return stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIME_OUT),
            initialValue = initValue
        )
    }

    protected abstract fun handleEvents(event: E)

    companion object {
        private const val STOP_TIME_OUT = 5000L
    }
}
