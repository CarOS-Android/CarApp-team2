package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R

@Composable
fun AutoHoldButton(
    modifier: Modifier = Modifier,
    isOpened: Boolean,
    onSwitch: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(23.dp))
            .background(color = Color.Unspecified, shape = RoundedCornerShape(23.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onSwitch
            )
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_auto_hold_background),
            contentDescription = ""
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_auto_hold_text),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.height(15.dp))
            Image(
                painter = painterResource(
                    id = if (isOpened) {
                        R.drawable.ic_auto_hold_indicator_opened
                    } else {
                        R.drawable.ic_auto_hold_indicator_closed
                    }
                ),
                contentDescription = ""
            )
        }
    }
}
