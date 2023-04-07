package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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

        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_ac_left),
                    contentDescription = null
                )
            }
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_ac_volume),
                    contentDescription = null
                )
            }
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_ac_right),
                    contentDescription = null
                )
            }
        }
    }

}