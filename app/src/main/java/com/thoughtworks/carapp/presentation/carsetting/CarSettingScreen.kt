package com.thoughtworks.carapp.presentation.carsetting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.presentation.carsetting.ac.AcMode
import com.thoughtworks.carapp.presentation.carsetting.fan.FanDirectionMode
import com.thoughtworks.carapp.presentation.carsetting.fragrance.FragranceController
import com.thoughtworks.carapp.presentation.carsetting.fragrance.FragranceEvent
import com.thoughtworks.carapp.presentation.carsetting.fragrance.FragranceViewModel
import com.thoughtworks.carapp.presentation.carsetting.hvacoptions.HvacOptionsMenu
import com.thoughtworks.carapp.presentation.main.seats.SeatController

@Composable
fun CarSettingScreen() {
    val fragranceViewModel: FragranceViewModel = viewModel()
    val isFragranceOn by fragranceViewModel.isFragranceOn.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        AcMode(isFragranceOn = isFragranceOn) { fragranceViewModel.sendEvent(FragranceEvent.SetFragranceOpenStatus) }
        HvacOptionsMenu()
        SeatController()
        FanDirectionMode()
        if (isFragranceOn) FragranceController(Modifier.align(Alignment.TopEnd), fragranceViewModel)
    }
}
