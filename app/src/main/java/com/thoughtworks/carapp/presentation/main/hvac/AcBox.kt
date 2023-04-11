package com.thoughtworks.carapp.presentation.main.hvac

import android.annotation.SuppressLint
import android.widget.TextView
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.blindhmi.ui.composable.border
import com.thoughtworks.blindhmi.ui.composable.center
import com.thoughtworks.blindhmi.ui.composable.indicator
import com.thoughtworks.blindhmi.ui.composable.stepper.ComposeBlindHMILoopStepper
import com.thoughtworks.blindhmi.ui.utils.DimensionUtils.px
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.domain.AcTemperatureUseCase

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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TemperatureButton(viewModel, leftTemperature, AcTemperatureUseCase.LEFT)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "主驾", color = Color.White, fontSize = 18.sp)
            }

            Image(
                painter = painterResource(id = R.drawable.ic_ac_volume),
                contentDescription = null
            )

            val rightTemperature by viewModel.rightTemperature.collectAsState()
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TemperatureButton(viewModel, rightTemperature, AcTemperatureUseCase.RIGHT)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "副驾", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

@SuppressLint("SetTextI18n")
@Composable
fun TemperatureButton(viewModel: AcViewModel, currentTemperature: Int, side: Int) {
    val context = LocalContext.current
    val centerTextView = remember {
        TextView(context).apply {
            textSize = 36.px.toFloat()
            setTextColor(android.graphics.Color.parseColor("#4E505E"))
        }
    }

    LaunchedEffect(key1 = currentTemperature, block = {
        centerTextView.text = "$currentTemperature°"
    })

    ComposeBlindHMILoopStepper(
        modifier = Modifier.size(144.dp),
        centerBackgroundRadius = 72.dp,
        centerBackgroundRes = R.drawable.ic_transparate,
        border = {
            border(context) {
                imageRes = R.drawable.ic_ac_temperature_border
                radius = 72.px
                drawOrder = getDrawOrder() + 1
            }
        },
        indicator = {
            indicator(context) {
                imageRes = R.drawable.ic_ac_temperature_indicator
                drawOrder = getDrawOrder() - 1
                radius = 72.px
            }
        },
        center = {
            center(context) {
                drawOrder = getDrawOrder() + 2
                contentViewFactory = {
                    centerTextView
                }
            }
        },
        minValue = 18f,
        maxValue = 30f,
        currentValue = currentTemperature.toFloat(),
        steps = 12,
        stepValue = 1f,
        onSweepStep = { _, stepperValue ->
            viewModel.sendEvent(AcEvent.SetTemperature(side, stepperValue.toInt()))
        }
    )
}