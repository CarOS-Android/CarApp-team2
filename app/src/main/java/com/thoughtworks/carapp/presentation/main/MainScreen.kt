package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.R

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val autoHoldState by viewModel.autoHoldUiState.collectAsState()
    val engineState by viewModel.engineState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        AutoHoldButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 55.dp),
            autoHoldState
        ) {
            viewModel.sendEvent(MainScreenEvent.SwitchAutoHoldMode)
        }

        Image(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 581.dp, top = 21.dp)
                .size(133.dp, 133.dp)
                .clickable {
                    if (engineState) {
                        viewModel.sendEvent(MainScreenEvent.StopEngineEvent)
                    } else {
                        viewModel.sendEvent(MainScreenEvent.StartEngineEvent)
                    }
                },
            painter = if (engineState) {
                painterResource(id = R.drawable.ic_engine_started)
            } else {
                painterResource(id = R.drawable.ic_engine_normal)
            },
            contentDescription = null
        )
    }
}
