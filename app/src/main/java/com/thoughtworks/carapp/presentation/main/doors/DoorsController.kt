package com.thoughtworks.carapp.presentation.main.doors

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.R

@Composable
fun DoorsController(viewModel: DoorsViewModel = viewModel()) {
    val isDoorsLocked by viewModel.isDoorsLocked.collectAsState()
    val isDoorRearLocked by viewModel.isDoorRearLocked.collectAsState()

    Column(modifier = Modifier.padding(start = 140.dp, top = 300.dp)) {
        DoorRearButton(
            modifier = Modifier.align(Alignment.End),
            isLocked = isDoorRearLocked
        ) {
            viewModel.sendEvent(DoorsEvent.SwitchDoorRearEvent)
        }

        Image(
            painter = painterResource(id = R.drawable.ic_car),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        DoorLockButton(
            modifier = Modifier
                .size(104.dp, 50.dp)
                .align(Alignment.CenterHorizontally),
            isLocked = isDoorsLocked
        ) {
            viewModel.sendEvent(DoorsEvent.SwitchDoorLockEvent)
        }
    }
}
