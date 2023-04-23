package com.thoughtworks.carapp.presentation.carsetting

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.RenderEffect
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.presentation.carsetting.ac.AcMode
import com.thoughtworks.carapp.presentation.carsetting.fan.FanDirectionMode
import com.thoughtworks.carapp.presentation.carsetting.fragrance.FragranceController
import com.thoughtworks.carapp.presentation.carsetting.fragrance.FragranceEvent
import com.thoughtworks.carapp.presentation.carsetting.fragrance.FragranceViewModel
import com.thoughtworks.carapp.presentation.carsetting.hvacoptions.HvacOptionsMenu
import com.thoughtworks.carapp.presentation.main.seats.SeatController

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun CarSettingScreen() {
    val fragranceViewModel: FragranceViewModel = viewModel()
    val isFragranceOn by fragranceViewModel.isFragranceOn.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        AcMode(isFragranceOn = isFragranceOn) { fragranceViewModel.sendEvent(FragranceEvent.SetFragranceOpenStatus) }
        HvacOptionsMenu()
        SeatController()
        FanDirectionMode()
        FragranceController(Modifier.align(Alignment.TopEnd), fragranceViewModel)
    }
}

fun Modifier.gesturesDisabled(disabled: Boolean = true) =
    if (disabled) {
        pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    awaitPointerEvent(pass = PointerEventPass.Initial)
                        .changes
                        .forEach(PointerInputChange::consume)
                }
            }
        }
    } else {
        this
    }

@RequiresApi(Build.VERSION_CODES.S)
fun Modifier.disabled(disabled: Boolean = true) =
    if (disabled) {
        graphicsLayer {
            renderEffect = RenderEffect
                .createColorFilterEffect(
                    BlendModeColorFilter(
                        Color.parseColor("#60444444"),
                        BlendMode.DST_IN
                    )
                )
                .asComposeRenderEffect()
        }
    } else {
        this
    }
