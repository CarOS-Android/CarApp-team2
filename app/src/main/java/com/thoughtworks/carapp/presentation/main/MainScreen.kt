package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val autoHoldState by viewModel.autoHoldUiState.collectAsState()

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AutoHoldButton(modifier = Modifier.padding(bottom = 55.dp), autoHoldState) {
            viewModel.sendEvent(MainScreenEvent.SwitchAutoHoldMode)
        }
    }
}
