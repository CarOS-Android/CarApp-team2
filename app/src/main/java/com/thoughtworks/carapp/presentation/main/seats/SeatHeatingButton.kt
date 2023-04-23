package com.thoughtworks.carapp.presentation.main.seats

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.presentation.theme.DarkGray
import com.thoughtworks.carapp.presentation.theme.LightBlack
import com.thoughtworks.carapp.presentation.theme.LightBlue
import com.thoughtworks.carapp.presentation.theme.LightRed
import com.thoughtworks.carapp.presentation.theme.MiddleGray

@Composable
fun SeatHeatingButton(
    heatValue: Int,
    onSwitch: () -> Unit = {}
) {
    val imgRes = if (heatValue == 0) {
        R.drawable.ic_seat_heating_off
    } else {
        R.drawable.ic_seat_heating_on
    }
    val activatedColor = LightRed
    val text = "Seat Heating"

    SeatButton(heatValue, onSwitch, imgRes, activatedColor, text)
}
@Composable
fun SeatVentilationButton(
    ventilationValue: Int,
    onSwitch: () -> Unit
) {
    val imgRes = if (ventilationValue == 0) {
        R.drawable.ic_seat_ventilation_closed
    } else {
        R.drawable.ic_seat_ventilation_activated
    }
    val activatedColor = LightBlue
    val text = "Seat Ventilation"

    SeatButton(ventilationValue, onSwitch, imgRes, activatedColor, text)
}

@Composable
private fun SeatButton(
    value: Int,
    onSwitch: () -> Unit,
    imgRes: Int,
    activatedColor: Color,
    text: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .size(width = 110.dp, height = 96.dp)
            .background(
                color = if (value == 0) DarkGray else MiddleGray,
                shape = RoundedCornerShape(18.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onSwitch
            )
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = imgRes),
                contentDescription = null
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_seat_status_item),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(if (value >= 1) activatedColor else LightBlack)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_seat_status_item),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(if (value == 2) activatedColor else LightBlack)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(text = text, modifier = Modifier, color = Color.White, fontSize = 10.sp)
        }
    }
}
