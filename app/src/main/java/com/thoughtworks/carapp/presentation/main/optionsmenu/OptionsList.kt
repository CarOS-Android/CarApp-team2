package com.thoughtworks.carapp.presentation.main.optionsmenu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.presentation.theme.DarkGray
import com.thoughtworks.carapp.presentation.theme.LightBlue
import com.thoughtworks.carapp.presentation.theme.LightGray

@Composable
fun OptionsList(viewModel: OptionsViewModel = viewModel()) {
    val windowLockUiState by viewModel.windowLockUiState.collectAsState()
    val fragranceUiState by viewModel.fragranceUiState.collectAsState()
    val wifiUiState by viewModel.wifiUiState.collectAsState()
    val bluetoothUiState by viewModel.bluetoothUiState.collectAsState()
    val cellularUiState by viewModel.cellularUiState.collectAsState()

    MenuContent(
        windowLockUiState,
        fragranceUiState,
        wifiUiState,
        bluetoothUiState,
        cellularUiState
    ) { viewModel.sendEvent(OptionsEvent.PerformOptionClick(it)) }
}

@Suppress("LongParameterList")
@Composable
private fun MenuContent(
    windowLockUiState: OptionUiState,
    fragranceUiState: OptionUiState,
    wifiUiState: OptionUiState,
    bluetoothUiState: OptionUiState,
    cellularUiState: OptionUiState,
    onClick: (Option) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        OptionButton(
            modifier = Modifier.padding(end = 35.dp),
            iconId = windowLockUiState.option.iconId,
            optionTextId = windowLockUiState.option.textId,
            isActive = windowLockUiState.isActive,
            isEnable = windowLockUiState.isEnable,
        ) { onClick(windowLockUiState.option) }

        OptionButton(
            modifier = Modifier.padding(end = 35.dp),
            iconId = fragranceUiState.option.iconId,
            optionTextId = fragranceUiState.option.textId,
            isActive = fragranceUiState.isActive,
            isEnable = fragranceUiState.isEnable,
        ) { onClick(fragranceUiState.option) }

        OptionButton(
            modifier = Modifier.padding(end = 35.dp),
            iconId = wifiUiState.option.iconId,
            optionTextId = wifiUiState.option.textId,
            isActive = wifiUiState.isActive,
            isEnable = wifiUiState.isEnable,
        ) { onClick(wifiUiState.option) }

        OptionButton(
            modifier = Modifier.padding(end = 35.dp),
            iconId = bluetoothUiState.option.iconId,
            optionTextId = bluetoothUiState.option.textId,
            isActive = bluetoothUiState.isActive,
            isEnable = bluetoothUiState.isEnable,
        ) { onClick(bluetoothUiState.option) }

        OptionButton(
            iconId = cellularUiState.option.iconId,
            optionTextId = cellularUiState.option.textId,
            isActive = cellularUiState.isActive,
            isEnable = cellularUiState.isEnable,
        ) { onClick(cellularUiState.option) }
    }
}

@Composable
private fun OptionButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    @StringRes optionTextId: Int,
    isActive: Boolean,
    isEnable: Boolean,
    onClick: () -> Unit = {}
) {
    val contentColor = if (isActive) LightBlue else Color.White

    Surface(
        modifier = modifier
            .size(width = 110.dp, height = 98.dp)
            .clickable(enabled = isEnable, onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = if (isEnable) DarkGray else LightGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(25.dp),
                painter = painterResource(id = iconId),
                contentDescription = "",
                tint = contentColor
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = stringResource(id = optionTextId),
                color = contentColor,
                textAlign = TextAlign.Center,
                lineHeight = 15.sp,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun EnabledOptionButtonPreview() {
    OptionButton(
        iconId = Option.WINDOW_LOCK.iconId,
        optionTextId = Option.WINDOW_LOCK.textId,
        isActive = true,
        isEnable = true
    )
}

@Preview
@Composable
fun DisabledOptionButtonPreview() {
    OptionButton(
        iconId = Option.WINDOW_LOCK.iconId,
        optionTextId = Option.WINDOW_LOCK.textId,
        isActive = false,
        isEnable = false
    )
}