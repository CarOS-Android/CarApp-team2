package com.thoughtworks.carapp.presentation.carsetting.ac

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.presentation.theme.DarkGray
import com.thoughtworks.carapp.presentation.theme.LightBlue

@Composable
fun AcMode(viewModel: AcViewModel = viewModel()) {
    val isAcPowerOn by viewModel.isAcPowerOn.collectAsState()
    val isAcAutoOn by viewModel.isAcAutoOn.collectAsState()
    val isAcOn by viewModel.isAcOn.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 166.dp, start = 72.dp)
    ) {
        AcButton(status = isAcPowerOn, isEnable = true, iconId = R.drawable.ic_ac_button) {
            viewModel.sendEvent(SettingScreenEvent.SwitchAcPowerEvent)
        }
        Spacer(modifier = Modifier.height(50.dp))

        AcButton(status = isAcOn, isEnable = isAcPowerOn, iconId = R.drawable.ic_ac_text) {
            viewModel.sendEvent(SettingScreenEvent.SwitchAcOnEvent)
        }
    }
    AcAutoButton(isAcAutoOn = isAcAutoOn) {
        viewModel.sendEvent(SettingScreenEvent.SwitchAcAutoEvent)
    }
}

@Composable
private fun AcButton(
    modifier: Modifier = Modifier,
    iconId: Int,
    status: Boolean,
    isEnable: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(width = 75.dp, height = 75.dp)
            .clickable(enabled = isEnable, onClick = onClick)
            .wrapContentSize(),
        contentAlignment = Alignment.Center

    ) {
        val bgColor = if (status) LightBlue else DarkGray
        Icon(
            painter = painterResource(id = R.drawable.ic_ac_bg),
            contentDescription = null,
            tint = bgColor
        )

        Image(
            painter = painterResource(id = iconId),
            contentDescription = null
        )
    }
}

@Composable
private fun AcAutoButton(
    isAcAutoOn: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(top = 362.dp, start = 72.dp)
            .clickable(
                onClick = onClick
            )
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        val bgColor = if (isAcAutoOn) LightBlue else DarkGray
        Icon(
            painter = painterResource(id = R.drawable.ic_ac_bg),
            contentDescription = null,
            tint = bgColor
        )
        Image(
            painter = painterResource(id = R.drawable.ic_ac_auto),
            contentDescription = null
        )
    }
}