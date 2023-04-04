package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.R

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val isAutoHoldOn by viewModel.isAutoHoldOn.collectAsState()
    val isEngineOn by viewModel.isEngineOn.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        AutoHoldButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 55.dp),
            isAutoHoldOn
        ) {
            viewModel.sendEvent(MainScreenEvent.SwitchAutoHoldModeEvent)
        }

        Image(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 752.dp, top = 32.dp)
                .size(200.dp, 200.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    if (isEngineOn) {
                        viewModel.sendEvent(MainScreenEvent.StopEngineEvent)
                    } else {
                        viewModel.sendEvent(MainScreenEvent.StartEngineEvent)
                    }
                },
            painter = if (isEngineOn) {
                painterResource(id = R.drawable.ic_engine_started)
            } else {
                painterResource(id = R.drawable.ic_engine_normal)
            },
            contentDescription = null
        )
    }
}
