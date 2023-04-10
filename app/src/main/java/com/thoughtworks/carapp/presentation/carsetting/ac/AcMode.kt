package com.thoughtworks.carapp.presentation.carsetting.ac

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.presentation.theme.DarkGray
import com.thoughtworks.carapp.presentation.theme.LightBlue

@Composable
fun AcMode(
    modifier: Modifier,
    isAcPowerOn: Boolean,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clickable(
                onClick = onClick
            )
            .wrapContentSize(),
        contentAlignment = Alignment.Center

    ) {
        val bgColor = if (isAcPowerOn) LightBlue else DarkGray
        Icon(
            painter = painterResource(id = R.drawable.ic_ac_bg),
            contentDescription = null,
            tint = bgColor
        )

        Image(
            painter = painterResource(id = R.drawable.ic_ac_button),
            contentDescription = null
        )
    }
}