package com.thoughtworks.carapp.presentation.main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.presentation.main.carlights.CarLightUI
import com.thoughtworks.carapp.presentation.main.components.AutoHoldButton
import com.thoughtworks.carapp.presentation.main.components.ClockAndSiri
import com.thoughtworks.carapp.presentation.main.components.EngineButton
import com.thoughtworks.carapp.presentation.main.components.ParkingBreakButton
import com.thoughtworks.carapp.presentation.main.components.VolumeButton
import com.thoughtworks.carapp.presentation.main.doors.DoorsController
import com.thoughtworks.carapp.presentation.main.hvac.AcBox
import com.thoughtworks.carapp.presentation.main.optionsmenu.OptionsList

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val isAutoHoldOn by viewModel.isAutoHoldOn.collectAsState()
    val isParkingBreakOn by viewModel.isParkingBreakOn.collectAsState()
    val isEngineOn by viewModel.isEngineStart.collectAsState()
    val clockText by viewModel.clockText.collectAsState()
    val isParking by viewModel.isParking.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        AcBox(
            modifier = Modifier
                .padding(bottom = 92.dp, start = 51.dp)
                .align(Alignment.BottomStart),
        )

        Column(
            modifier = Modifier
                .padding(bottom = 92.dp, end = 888.dp)
                .align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ParkingBreakButton(isParkingBreakOn) {
                viewModel.sendEvent(MainScreenEvent.SwitchParkingBreakEvent)
            }
            Spacer(modifier = Modifier.height(43.dp))
            AutoHoldButton(isAutoHoldOn) {
                viewModel.sendEvent(MainScreenEvent.SwitchAutoHoldEvent)
            }
        }

        ConstraintLayout(createClockAndSiriConstraints()) {
            ClockAndSiri(clockText)
            EngineButton(isEngineOn) { viewModel.sendEvent(MainScreenEvent.EngineClickedEvent) }
            VolumeButton()
        }

        Column(
            modifier = Modifier
                .padding(top = 86.dp, end = 80.dp, bottom = 90.dp)
                .width(690.dp)
                .fillMaxHeight()
                .align(Alignment.CenterEnd),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            NavigationMap()
            MusicPlay()
            OptionsList()
        }

        Crossfade(targetState = isParking) { isParking ->
            if (isParking) DoorsController() else CarLightUI()
        }
    }
}

private fun createClockAndSiriConstraints(): ConstraintSet {
    return ConstraintSet {
        val topGuideLine = createGuidelineFromTop(141.dp)

        val clock = createRefFor("clock")
        val siri = createRefFor("siri")
        val volume = createRefFor("volume")
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
        constrain(volume) {
            top.linkTo(topGuideLine)
            bottom.linkTo(topGuideLine)
            start.linkTo(siri.end)
        }
        constrain(engine) {
            top.linkTo(topGuideLine)
            bottom.linkTo(topGuideLine)
            start.linkTo(parent.start, 752.dp)
        }
    }
}

@Composable
private fun MusicPlay() {
    Image(
        painter = painterResource(id = R.drawable.img_music),
        contentDescription = null
    )
}

@Composable
private fun NavigationMap() {
    Image(
        painter = painterResource(id = R.drawable.img_nav_map),
        contentDescription = ""
    )
}
