package com.thoughtworks.carapp.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import com.thoughtworks.carapp.R

@Composable
fun VolumeButton() {
    Image(
        painter = painterResource(id = R.drawable.ic_volume),
        contentDescription = "",
        modifier = Modifier.layoutId("volume")
    )
}