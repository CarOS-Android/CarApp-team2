package com.thoughtworks.carapp.presentation.carsetting.ac

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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

        AcButton(
            status = isAcPowerOn && isAcOn,
            isEnable = isAcPowerOn,
            iconId = R.drawable.ic_ac_text
        ) {
            viewModel.sendEvent(SettingScreenEvent.SwitchAcOnEvent)
        }
        Spacer(modifier = Modifier.height(50.dp))

        AcButton(
            status = isAcPowerOn && isAcAutoOn,
            isEnable = isAcPowerOn,
            iconId = R.drawable.ic_ac_auto
        ) {
            viewModel.sendEvent(SettingScreenEvent.SwitchAcAutoEvent)
        }
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
            .size(width = 80.dp, height = 80.dp)
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