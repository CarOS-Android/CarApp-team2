package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.presentation.main.optionsmenu.OptionsList

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val isAutoHoldOn by viewModel.isAutoHoldOn.collectAsState()
    val isParkingBreakOn by viewModel.isParkingBreakOn.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
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
                viewModel.sendEvent(MainScreenEvent.SwitchAutoHoldModeEvent)
            }
        }

        ConstraintLayout(createConstraints()) {
            ClockAndSiri(viewModel)
            EngineButton(viewModel)
        }

        Column(
            modifier = Modifier
                .padding(top = 86.dp, end = 80.dp, bottom = 90.dp)
                .width(690.dp)
                .align(Alignment.CenterEnd)
        ) {
            viewModel.sendEvent(MainScreenEvent.SwitchParkingBreakEvent)
            NavigationMap()
            Spacer(modifier = Modifier.height(252.dp))
            OptionsList()
        }
    }
}

private fun createConstraints(): ConstraintSet {
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

@Composable
private fun NavigationMap() {
    Image(
        painter = painterResource(id = R.drawable.img_nav_map),
        contentDescription = ""
    )
}

@Preview(showBackground = true, device = Devices.DESKTOP)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
