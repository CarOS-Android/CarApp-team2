package com.thoughtworks.carapp.presentation.carsetting.fan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.blindhmi.ui.composable.border
import com.thoughtworks.blindhmi.ui.composable.center
import com.thoughtworks.blindhmi.ui.composable.indicator
import com.thoughtworks.blindhmi.ui.composable.stepper.ComposeBlindHMILoopStepper
import com.thoughtworks.blindhmi.ui.utils.DimensionUtils.px
import com.thoughtworks.carapp.R

import com.thoughtworks.carapp.presentation.theme.LightBlue

@Composable

fun FanMode(
    modifier: Modifier = Modifier, viewModel: FanViewModel = viewModel()
) {
    Box(
        modifier = modifier
            .padding(top = 390.dp, start = 420.dp)
            .size(width = 105.dp, height = 110.dp)
    ) {
        val fanDirection by viewModel.fanDirection.collectAsState()

        FanButton(fanDirection) { viewModel.sendEvent(FanEvent.SetFanDirection(it)) }

        Text(
            text = "$fanDirection",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(width = 80.dp, height = 30.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(LightBlue),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FanButton(
    fanDirection: Int, onSweep: (Int) -> Unit
) {
    val context = LocalContext.current
    ComposeBlindHMILoopStepper(modifier = Modifier.size(104.dp),
        centerBackgroundRadius = 44.dp,
        centerBackgroundRes = R.drawable.fan_bg,
        border = {
            border(context) {
                imageRes = R.drawable.fan_border
                radius = 52.px
                drawOrder = getDrawOrder()
            }
        },
        indicator = {
            indicator(context) {
                imageRes = R.drawable.fan_indicator
                drawOrder = getDrawOrder() - 1
                radius = 52.px
            }
        },
        center = {
            center(context) {
                contentFactory = {
                    Image(
                        painter = painterResource(
                            R.drawable.fan_icon
                        ), contentDescription = ""
                    )
                }
                drawOrder = getDrawOrder() + 2
            }
        },
        minValue = 1f,
        maxValue = 3f,
        currentValue = fanDirection.toFloat(),
        steps = 12,
        stepValue = 1f,
        onSweepStep = { _, stepperValue ->
            onSweep(stepperValue.toInt())
        })
}


