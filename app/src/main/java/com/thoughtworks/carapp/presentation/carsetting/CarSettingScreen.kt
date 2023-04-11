package com.thoughtworks.carapp.presentation.carsetting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thoughtworks.carapp.presentation.carsetting.ac.AcMode
import com.thoughtworks.carapp.presentation.main.seats.SeatController

@Composable
fun CarSettingScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        AcMode()
        SeatController()
    }
}
