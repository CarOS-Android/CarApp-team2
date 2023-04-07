package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.thoughtworks.carapp.R

@Composable
fun DoorRearButton(
    modifier: Modifier,
    isLocked: Boolean,
    onSwitch: () -> Unit = {}
) {
    IconButton(
        onClick = onSwitch,
        modifier = modifier
            .background(
                color = if (isLocked) {
                    Color(0xFF606060)
                } else {
                    Color(0xFF25BEFA)
                },
                shape = CircleShape
            )
    ) {
        Icon(
            painter = painterResource(
                id = if (isLocked) {
                    R.drawable.ic_door_lock
                } else {
                    R.drawable.ic_door_unlock
                }
            ),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}
