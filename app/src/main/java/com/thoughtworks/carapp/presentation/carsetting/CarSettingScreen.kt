package com.thoughtworks.carapp.presentation.carsetting

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.RenderEffect
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.presentation.carsetting.ac.AcMode
import com.thoughtworks.carapp.presentation.carsetting.ac.AcViewModel
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
        HvacControllerPanel()
        SeatController()
        FragranceController(Modifier.align(Alignment.TopEnd), fragranceViewModel)
        ReadingLight()
    }
}

@Composable
fun BoxScope.ReadingLight() {
    Image(
        painter = painterResource(id = R.drawable.img_reading_light),
        contentDescription = "",
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(end = 98.dp, bottom = 88.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HvacControllerPanel() {
    val acViewModel: AcViewModel = viewModel()
    val isAcPowerOn by acViewModel.isAcPowerOn.collectAsState()
    val isAcAutoOn by acViewModel.isAcAutoOn.collectAsState()

    HvacOptionsMenu(isAcPowerOn, isAcAutoOn)
    FanDirectionMode(isAcPowerOn, isAcAutoOn)
    Image(
        painter = painterResource(id = R.drawable.img_hvac_panel),
        contentDescription = "",
        modifier = Modifier
            .padding(top = 165.dp, start = 353.dp)
            .disabled(disabled = if (!isAcPowerOn) true else isAcAutoOn)
    )
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
