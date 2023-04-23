package com.thoughtworks.carapp.presentation.carsetting.ac

import androidx.annotation.DrawableRes
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.R

private const val DISABLED_STATUS_ALPHA = 0.4f

@Composable
fun AcMode(
    viewModel: AcViewModel = viewModel(),
    isFragranceOn: Boolean,
    onFragranceButtonClick: () -> Unit
) {
    val isAcPowerOn by viewModel.isAcPowerOn.collectAsState()
    val isAcAutoOn by viewModel.isAcAutoOn.collectAsState()
    val isAcOn by viewModel.isAcOn.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 166.dp, start = 72.dp)
    ) {
        AcButton(
            isActivated = isAcPowerOn,
            activatedRes = R.drawable.hvac_power_switch_activated,
            closedRes = R.drawable.hvac_power_switch_closed,
            isEnable = true
        ) { viewModel.sendEvent(SettingScreenEvent.SwitchAcPowerEvent) }
        Spacer(modifier = Modifier.height(48.dp))

        AcButton(
            isActivated = isAcOn,
            activatedRes = R.drawable.hvac_ac_activated,
            closedRes = R.drawable.hvac_ac_closed,
            isEnable = isAcPowerOn && isAcAutoOn.not()
        ) { viewModel.sendEvent(SettingScreenEvent.SwitchAcOnEvent) }
        Spacer(modifier = Modifier.height(48.dp))

        AcButton(
            isActivated = isAcAutoOn,
            activatedRes = R.drawable.hvac_auto_activated,
            closedRes = R.drawable.hvac_auto_closed,
            isEnable = isAcPowerOn
        ) { viewModel.sendEvent(SettingScreenEvent.SwitchAcAutoEvent) }
        Spacer(modifier = Modifier.height(48.dp))

        AcButton(
            isActivated = isFragranceOn,
            activatedRes = R.drawable.hvac_fragrance_activated,
            closedRes = R.drawable.hvac_fragrance_closed,
            isEnable = isAcPowerOn
        ) { onFragranceButtonClick() }
    }
}

@Composable
private fun AcButton(
    isActivated: Boolean,
    @DrawableRes activatedRes: Int,
    @DrawableRes closedRes: Int,
    isEnable: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clickable(enabled = isEnable, onClick = onClick)
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = if (isActivated) activatedRes else closedRes),
            contentDescription = null,
            alpha = if (isEnable) 1f else DISABLED_STATUS_ALPHA
        )
    }
}