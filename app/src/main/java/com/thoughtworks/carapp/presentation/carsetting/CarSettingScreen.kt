package com.thoughtworks.carapp.presentation.carsetting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.presentation.carsetting.ac.AcMode

@Composable
fun CarSettingScreen(viewModel: SettingViewModel = viewModel()) {
    val isAcPowerOn by viewModel.isAcPowerOn.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        AcMode(
            modifier = Modifier
                .padding(top = 166.dp, start = 72.dp),
            isAcPowerOn
        ) {
            viewModel.sendEvent(SettingScreenEvent.SwitchAcPowerEvent)
        }
    }
}