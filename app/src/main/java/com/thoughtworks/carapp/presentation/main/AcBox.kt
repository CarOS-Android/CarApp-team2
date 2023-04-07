package com.thoughtworks.carapp.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.presentation.theme.WhiteText


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
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            item {
                AcButton(R.drawable.ic_ac_left, R.string.ac_left)
            }
            item {
                AcButton(R.drawable.ic_ac_level, R.string.ac_level)
            }
            item {
                AcButton(R.drawable.ic_ac_left, R.string.ac_right)
            }
        }
    }

}

@Composable
fun AcButton(
    @DrawableRes iconId: Int,
    @StringRes textId: Int,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null
        )

        Spacer(modifier = modifier.height(8.dp))

        Text(
            text = stringResource(id = textId),
            color = WhiteText,
            textAlign = TextAlign.Center,
            lineHeight = 25.sp,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            maxLines = 1
        )
    }
}