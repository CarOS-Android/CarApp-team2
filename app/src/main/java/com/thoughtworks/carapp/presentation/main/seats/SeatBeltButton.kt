package com.thoughtworks.carapp.presentation.main.seats

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.presentation.theme.DarkGray

@Composable
fun SeatBeltButton(
    seatBeltStatus: Boolean,
    onSwitch: () -> Unit = {}
) {

    val imgRes = if (seatBeltStatus) {
        R.drawable.ic_seat_img
    } else {
        R.drawable.ic_seat_heating_on
    }

    val text = "Seat Belt"

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .size(width = 110.dp, height = 96.dp)
            .background(
                color = DarkGray,
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
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp, 40.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = text, modifier = Modifier, color = Color.White, fontSize = 10.sp)
        }
    }
}
