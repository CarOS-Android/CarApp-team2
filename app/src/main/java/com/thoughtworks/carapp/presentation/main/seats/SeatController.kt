package com.thoughtworks.carapp.presentation.main.seats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.domain.model.SeatFuncGear
import com.thoughtworks.carapp.presentation.theme.MiddleDarkGray

@Composable
fun SeatController(viewModel: SeatViewModel = viewModel()) {
    val driverHeatStatus by viewModel.driverSeatHeatStatus.collectAsState()
    val copilotHeatStatus by viewModel.copilotSeatHeatStatus.collectAsState()
    val driverVentilationStatus by viewModel.driverSeatVentilationStatus.collectAsState()
    val copilotVentilationStatus by viewModel.copilotSeatVentilationStatus.collectAsState()
    val driverSeatBeltStatus by viewModel.driverSeatBeltStatus.collectAsState()

    Row(modifier = Modifier.padding(start = 73.dp, top = 709.dp)) {
        val driverTitle = "主驾"
        val copilotTitle = "副驾"
        SeatPanel(
            driverTitle = driverTitle,
            driverHeatStatus = driverHeatStatus,
            driverVentilationStatus = driverVentilationStatus,
            driverSeatBeltStatus = driverSeatBeltStatus,
            onDriverHeatSwitch = { viewModel.sendEvent(SeatEvent.SwitchDriverSeatHeatingEvent) },
            onDriverVentilationSwitch = { viewModel.sendEvent(SeatEvent.SwitchDriverVentilationEvent) },
            onDriverBeltWear = { viewModel.sendEvent(SeatEvent.WearDriverSeatBeltEvent) },
        )
        Spacer(modifier = Modifier.width(40.dp))
        SeatPanel(
            driverTitle = copilotTitle,
            driverHeatStatus = copilotHeatStatus,
            driverVentilationStatus = copilotVentilationStatus,
            driverSeatBeltStatus = driverSeatBeltStatus,
            onDriverHeatSwitch = { viewModel.sendEvent(SeatEvent.SwitchCopilotSeatHeatingEvent) },
            onDriverVentilationSwitch = { viewModel.sendEvent(SeatEvent.SwitchCopilotVentilationEvent) },
            onDriverBeltWear = { viewModel.sendEvent(SeatEvent.WearDriverSeatBeltEvent) },
        )
    }
}

@Composable
private fun SeatPanel(
    driverTitle: String,
    driverHeatStatus: SeatFuncGear,
    driverVentilationStatus: SeatFuncGear,
    driverSeatBeltStatus: Boolean,
    onDriverHeatSwitch: () -> Unit,
    onDriverVentilationSwitch: () -> Unit,
    onDriverBeltWear: () -> Unit
) {
    Row(
        modifier = Modifier
            .size(width = 605.dp, height = 283.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = MiddleDarkGray)
    ) {
        Column(
            modifier = Modifier.padding(start = 44.dp, top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.ic_seat_img), contentDescription = null)
            Text(text = driverTitle, color = Color.White, fontSize = 28.sp)
        }
        Spacer(modifier = Modifier.width(54.dp))

        Row(modifier = Modifier.padding(top = 32.dp)) {
            SeatHeatingButton(driverHeatStatus.value, onDriverHeatSwitch)
            Spacer(modifier = Modifier.width(30.dp))
            SeatVentilationButton(driverVentilationStatus.value, onDriverVentilationSwitch)
            Spacer(modifier = Modifier.width(30.dp))
            SeatBeltButton(driverSeatBeltStatus, onDriverBeltWear)
        }
    }
}
