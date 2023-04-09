package com.thoughtworks.carapp.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.presentation.theme.WhiteGrey

@Composable
fun ClockAndSiri(clockText: String) {
    Text(
        modifier = Modifier
            .layoutId("clock")
            .width(240.dp),
        text = clockText,
        fontSize = 96.sp,
        color = WhiteGrey
    )
    Image(
        modifier = Modifier.layoutId("siri"),
        painter = painterResource(R.drawable.ic_siri),
        contentDescription = null
    )
}