package com.thoughtworks.carapp.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

interface Event

abstract class BaseViewModel : ViewModel() {
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

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

    fun sendEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    abstract fun handleEvents(event: Event)
}
