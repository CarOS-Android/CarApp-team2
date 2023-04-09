package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R

@Composable
fun AcBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(width = 663.dp, height = 228.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_temperature_bg),
            contentDescription = null
        )

        Image(
            painter = painterResource(id = R.drawable.ic_temperature_bg_rec),
            contentDescription = null
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 14.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_ac_left),
                contentDescription = null
            )
            Image(
                painter = painterResource(id = R.drawable.ic_ac_volume),
                contentDescription = null
            )
            Image(
                painter = painterResource(id = R.drawable.ic_ac_right),
                contentDescription = null
            )
        }
    }
}