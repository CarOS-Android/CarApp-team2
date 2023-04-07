package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.R

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val isAutoHoldOn by viewModel.isAutoHoldOn.collectAsState()
    val isEngineOn by viewModel.isEngineOn.collectAsState()
    val isDoorLockOn by viewModel.isDoorLockOn.collectAsState()
    val isDoorRearOn by viewModel.isDoorRearOn.collectAsState()

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

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 140.dp, top = 350.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_car),
                contentDescription = null
            )

            DoorLockButton(modifier = Modifier.size(104.dp, 50.dp), isDoorLockOn) {
                viewModel.sendEvent(MainScreenEvent.SwitchDoorLockEvent)
            }
        }

        DoorRearButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 725.dp, top = 302.dp)
                .size(16.8.dp, 22.4.dp),
            isDoorRearOn == 0
        ) {
            viewModel.sendEvent(MainScreenEvent.SwitchDoorRearEvent)
        }

        Image(
            painter = painterResource(id = R.drawable.ic_map),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 1030.dp, top = 86.dp)
                .size(690.dp, 556.dp)
                .background(color = Color.Unspecified)
        )
    }
}
