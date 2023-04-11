package com.thoughtworks.carapp.presentation.main.seats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
    val isSeatHeatingOpened by viewModel.isSeatHeatingOpened.collectAsState()

    Box(
        modifier = Modifier
            .padding(start = 73.dp, top = 709.dp)
            .background(color = Color.DarkGray)
            .size(width = 605.dp, height = 283.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(start = 44.5.dp, top = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.ic_seat_img), contentDescription = null)
                Text(text = "主驾", color = Color.White, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(58.44.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                SeatHeatingButton(isSeatHeatingOpened) {
                    viewModel.sendEvent(SeatEvent.SwitchSeatHeatingEvent)
                }
            }
        }
    }
}
