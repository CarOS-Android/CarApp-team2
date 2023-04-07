package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.thoughtworks.carapp.R

@Composable
fun CarLightUI(viewModel: MainViewModel) {
    val isHazardLightOn by viewModel.isHazardLightOn.collectAsState()
    val isHeadLightOn by viewModel.isHeadLightOn.collectAsState()
    val isHighBeamLightOn by viewModel.isHighBeamLightOn.collectAsState()

    ConstraintLayout(createCarLightsConstraints()) {
        Image(
            modifier = Modifier.layoutId("car"),
            painter = painterResource(id = R.drawable.ic_car_lights_on),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .layoutId("hazardLightButton")
                .size(width = 110.dp, height = 76.dp)
                .background(
                    colorResource(id = R.color.light_button_background_color),
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(horizontal = 30.dp, vertical = 24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    viewModel.sendEvent(MainScreenEvent.HazardLightEvent)
                },
            painter = if (isHazardLightOn) {
                painterResource(id = R.drawable.ic_hazard_light_on)
            } else {
                painterResource(id = R.drawable.ic_hazard_light_off)
            },
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .layoutId("headLightButton")
                .padding(start = 32.dp, end = 32.dp)
                .size(width = 110.dp, height = 76.dp)
                .background(
                    colorResource(id = R.color.light_button_background_color),
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(horizontal = 31.dp, vertical = 13.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }, indication = null
                ) {
                    viewModel.sendEvent(MainScreenEvent.HeadLightEvent)
                }, painter = if (isHeadLightOn) {
                painterResource(id = R.drawable.ic_head_light_on)
            } else {
                painterResource(id = R.drawable.ic_head_light_off)
            },
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .layoutId("highBeamLightButton")
                .size(width = 110.dp, height = 76.dp)
                .background(
                    colorResource(id = R.color.light_button_background_color),
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(horizontal = 31.dp, vertical = 13.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    viewModel.sendEvent(MainScreenEvent.HighBeamLightEvent)
                },
            painter = if (isHighBeamLightOn) {
                painterResource(id = R.drawable.ic_high_beam_light_on)
            } else {
                painterResource(id = R.drawable.ic_high_beam_light_off)
            },
            contentDescription = null
        )
    }
}

private fun createCarLightsConstraints(): ConstraintSet {
    return ConstraintSet {
        val topGuideLine = createGuidelineFromTop(544.dp)
        val centerGuideLine = createGuidelineFromStart(470.dp)

        val car = createRefFor("car")
        val hazardLightButton = createRefFor("hazardLightButton")
        val headLightButton = createRefFor("headLightButton")
        val highBeamLightButton = createRefFor("highBeamLightButton")

        constrain(car) {
            bottom.linkTo(topGuideLine)
            start.linkTo(centerGuideLine)
            end.linkTo(centerGuideLine)
        }

        constrain(hazardLightButton) {
            top.linkTo(car.bottom, 43.dp)
        }

        constrain(headLightButton) {
            top.linkTo(hazardLightButton.top)
            bottom.linkTo(hazardLightButton.bottom)
        }

        constrain(highBeamLightButton) {
            top.linkTo(hazardLightButton.top)
            bottom.linkTo(hazardLightButton.bottom)
        }

        val lightChain = createHorizontalChain(hazardLightButton, headLightButton, highBeamLightButton)
        constrain(lightChain) {
            start.linkTo(car.start)
            end.linkTo(car.end)
        }
    }
}