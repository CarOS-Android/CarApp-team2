package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.R

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val isAutoHoldOn by viewModel.isAutoHoldOn.collectAsState()
    val isParkingBreakOn by viewModel.isParkingBreakOn.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        AutoHoldButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 55.dp),
            isAutoHoldOn
        ) {
            viewModel.sendEvent(MainScreenEvent.SwitchAutoHoldModeEvent)
        }

        ParkingBreakButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 200.dp),
            isParkingBreakOn
        ) {
            viewModel.sendEvent(MainScreenEvent.SwitchParkingBreakEvent)
        }

        ConstraintLayout(createClockAndSiriConstraints()) {
            ClockAndSiri(viewModel)
            EngineButton(viewModel)
        }

        CarLightUI(viewModel)
    }
}

@Composable
private fun ClockAndSiri(viewModel: MainViewModel) {
    val clockText by viewModel.clockText.collectAsState()
    Text(
        modifier = Modifier
            .layoutId("clock")
            .width(240.dp),
        text = clockText,
        fontSize = 96.sp,
        color = colorResource(id = R.color.clock_color)
    )
    Image(
        modifier = Modifier.layoutId("siri"),
        painter = painterResource(R.drawable.ic_siri),
        contentDescription = null
    )
}

@Composable
private fun EngineButton(viewModel: MainViewModel) {
    val isEngineOn by viewModel.isEngineOn.collectAsState()
    Image(
        modifier = Modifier
            .layoutId("engine")
            .size(200.dp, 200.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (isEngineOn) {
                    viewModel.sendEvent(MainScreenEvent.StopEngineEvent)
                } else {
                    viewModel.sendEvent(MainScreenEvent.StartEngineEvent)
                }
            },
        painter = if (isEngineOn) {
            painterResource(id = R.drawable.ic_engine_started)
        } else {
            painterResource(id = R.drawable.ic_engine_normal)
        },
        contentDescription = null
    )
}

private fun createClockAndSiriConstraints(): ConstraintSet {
    return ConstraintSet {
        val topGuideLine = createGuidelineFromTop(141.dp)

        val clock = createRefFor("clock")
        val siri = createRefFor("siri")
        val engine = createRefFor("engine")

        constrain(clock) {
            top.linkTo(topGuideLine)
            bottom.linkTo(topGuideLine)
            start.linkTo(parent.start, 48.dp)
        }
        constrain(siri) {
            top.linkTo(topGuideLine)
            bottom.linkTo(topGuideLine)
            start.linkTo(clock.end)
        }
        constrain(engine) {
            top.linkTo(topGuideLine)
            bottom.linkTo(topGuideLine)
            start.linkTo(parent.start, 752.dp)
        }
    }
}
