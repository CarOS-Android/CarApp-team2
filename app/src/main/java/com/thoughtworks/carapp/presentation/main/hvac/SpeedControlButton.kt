package com.thoughtworks.carapp.presentation.main.hvac

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.blindhmi.ui.component.container.stepper.Stepper
import com.thoughtworks.blindhmi.ui.composable.border
import com.thoughtworks.blindhmi.ui.composable.center
import com.thoughtworks.blindhmi.ui.composable.indicator
import com.thoughtworks.blindhmi.ui.composable.stepper.ComposeBlindHMILoopStepper
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.domain.hvac.FanSpeed

@Composable
fun SpeedControlButton(
    fanSpeed: FanSpeed,
    onSweep: (Int) -> Unit
) {
    val context = LocalContext.current
    val borderRadiusPx = with(LocalDensity.current) {
        59.dp.toPx().toInt()
    }
    val indicatorRadiusPx = with(LocalDensity.current) {
        72.dp.toPx().toInt()
    }
    var currentFanSpeed by remember { mutableStateOf(fanSpeed) }

    ComposeBlindHMILoopStepper(
        modifier = Modifier.size(144.dp),
        centerHotspotRadius = 28.dp,
        centerBackgroundRadius = 24.dp,
        centerBackgroundRes = R.drawable.ic_transparate,
        steps = 3,
        currentValue = fanSpeed.value.toFloat(),
        stepOrientation = Stepper.CLOCKWISE,
        minValue = FanSpeed.OFF.value.toFloat(),
        maxValue = FanSpeed.SPEED_MAX.value.toFloat(),
        stepValue = 1f,
        border = {
            border(context) {
                imageRes = R.drawable.ic_hvac_fan_speed_center_bg
                radius = borderRadiusPx
                drawOrder = getDrawOrder() + 1
            }
        },
        indicator = {
            indicator(context) {
                imageRes = R.drawable.ic_hvac_fan_speed_indicator
                radius = indicatorRadiusPx
                drawOrder = getDrawOrder() - 1
            }
        },
        center = {
            center(context) {
                contentFactory = {
                    Image(
                        painter = painterResource(
                            when (currentFanSpeed) {
                                FanSpeed.OFF -> R.drawable.ic_hvac_fan_speed_center_state_off
                                FanSpeed.SPEED_1 -> R.drawable.ic_hvac_fan_speed_center_1
                                FanSpeed.SPEED_2 -> R.drawable.ic_hvac_fan_speed_center_2
                                FanSpeed.SPEED_3 -> R.drawable.ic_hvac_fan_speed_center_3
                                FanSpeed.SPEED_MAX -> R.drawable.ic_hvac_fan_speed_center_4
                            }
                        ),
                        contentDescription = ""
                    )
                }
                drawOrder = getDrawOrder() + 2
            }
        },
        onSweepStep = { _, stepperValue ->
            onSweep(stepperValue.toInt())
        }
    )

    LaunchedEffect(fanSpeed) {
        currentFanSpeed = fanSpeed
    }
}