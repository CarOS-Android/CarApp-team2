package com.thoughtworks.carapp.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R

@Composable
fun EngineButton(isEngineOn: Boolean, onClick: () -> Unit) {
    Image(
        modifier = Modifier
            .layoutId("engine")
            .size(200.dp, 200.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        painter = if (isEngineOn) {
            painterResource(id = R.drawable.ic_engine_started)
        } else {
            painterResource(id = R.drawable.ic_engine_normal)
        },
        contentDescription = null
    )
}
