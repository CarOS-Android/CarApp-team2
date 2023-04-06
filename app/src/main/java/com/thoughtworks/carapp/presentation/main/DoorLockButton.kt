package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R

@Composable
fun DoorLockButton(
    modifier: Modifier = Modifier,
    isLocked: Boolean,
    onSwitch: () -> Unit = {}
) {
    Button(
        onClick = onSwitch,
        modifier = modifier
            .clip(RoundedCornerShape(25.dp))
            .background(color = Color.Unspecified), shape = RoundedCornerShape(25.dp),
        colors = if (isLocked) {
            ButtonDefaults.buttonColors(backgroundColor = Color(0xFF606060))
        } else {
            ButtonDefaults.buttonColors(backgroundColor = Color(0xFF25BEFA))
        }
    ) {
        Image(
            painter = painterResource(
            id = if (isLocked) {
                R.drawable.ic_door_lock
            } else {
                R.drawable.ic_door_unlock
            }),
            contentDescription = null
        )
    }
}