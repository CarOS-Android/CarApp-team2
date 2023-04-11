package com.thoughtworks.carapp.presentation.main.hvac

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.presentation.theme.WhiteText

@Composable
fun AcBox(modifier: Modifier = Modifier, viewModel: AcViewModel = viewModel()) {
    Box(
        modifier = modifier
            .size(width = 663.dp, height = 228.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_temperature_bg),
            contentDescription = null
        )

        Image(
            painter = painterResource(id = R.drawable.ic_temperature_bg_rec),
            contentDescription = null
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 14.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val leftTemperature by viewModel.leftTemperature.collectAsState()
            val rightTemperature by viewModel.rightTemperature.collectAsState()
            val fanSpeed by viewModel.fanSpeed.collectAsState()

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TemperatureButton(leftTemperature) {
                    viewModel.sendEvent(AcEvent.SetLeftSideTemperature(it))
                }
                Spacer(modifier = Modifier.height(8.dp))
                LabelText(labelId = R.string.hvac_ac_temperature_left)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SpeedControlButton(fanSpeed) {
                    viewModel.sendEvent(AcEvent.SetFanSpeed(it))
                }
                Spacer(modifier = Modifier.height(8.dp))
                LabelText(labelId = R.string.hvac_fan_speed)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TemperatureButton(rightTemperature) {
                    viewModel.sendEvent(AcEvent.SetRightSideTemperature(it))
                }
                Spacer(modifier = Modifier.height(8.dp))
                LabelText(labelId = R.string.hvac_ac_temperature_right)
            }
        }
    }
}

@Composable
fun LabelText(@StringRes labelId: Int) {
    Text(
        text = stringResource(labelId),
        color = WhiteText,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        lineHeight = 25.sp
    )
}