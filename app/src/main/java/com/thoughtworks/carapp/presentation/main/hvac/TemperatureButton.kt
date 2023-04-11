package com.thoughtworks.carapp.presentation.main.hvac

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.thoughtworks.blindhmi.ui.composable.border
import com.thoughtworks.blindhmi.ui.composable.center
import com.thoughtworks.blindhmi.ui.composable.indicator
import com.thoughtworks.blindhmi.ui.composable.stepper.ComposeBlindHMILoopStepper
import com.thoughtworks.blindhmi.ui.utils.DimensionUtils.px
import com.thoughtworks.carapp.R

@SuppressLint("SetTextI18n")
@Composable
fun TemperatureButton(currentTemperature: Int, onSweep: (Int) -> Unit) {
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
            onSweep(stepperValue.toInt())
        }
    )
}
