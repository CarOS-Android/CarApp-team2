package com.thoughtworks.carapp.presentation.main.seats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.R

@Composable
fun SeatController(viewModel: SeatViewModel = viewModel()) {
    val driverHeatStatus by viewModel.driverSeatHeatStatus.collectAsState()
    val copilotHeatStatus by viewModel.copilotSeatHeatStatus.collectAsState()

    Row(modifier = Modifier.padding(start = 73.dp, top = 709.dp)) {
        val driverTitle = "主驾"
        val copilotTitle = "副驾"
        SeatPanel(driverTitle, driverHeatStatus) {
            viewModel.sendEvent(SeatEvent.SwitchDriverSeatHeatingEvent)
        }
        Spacer(modifier = Modifier.width(40.dp))
        SeatPanel(copilotTitle, copilotHeatStatus) {
            viewModel.sendEvent(SeatEvent.SwitchCopilotSeatHeatingEvent)
        }
    }
}

@Composable
private fun SeatPanel(driverTitle: String, driverHeatStatus: Int, onDriverSwitch: () -> Unit) {
    Row(
        modifier = Modifier
            .size(width = 605.dp, height = 283.dp)
            .background(color = Color.DarkGray)
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
            SeatHeatingButton(driverHeatStatus, onDriverSwitch)
        }
    }
}
