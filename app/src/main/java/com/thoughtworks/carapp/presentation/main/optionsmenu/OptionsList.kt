package com.thoughtworks.carapp.presentation.main.optionsmenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

@Composable
fun OptionsList(viewModel: OptionsViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    MenuContent(uiState) {
        viewModel.sendEvent(OptionsEvent.PerformOptionClick(it))
    }
}

@Suppress("LongParameterList")
@Composable
private fun MenuContent(
    state: OptionsUiState,
    onClick: (Option) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        state.options.forEachIndexed { index, optionUiState ->
            OptionButton(
                option = optionUiState.option,
                isActive = optionUiState.isActive,
                isEnable = optionUiState.isEnable,
            ) { onClick(optionUiState.option) }

            if (index != state.options.lastIndex) Spacer(modifier = Modifier.width(35.dp))
        }
    }
}

@Composable
private fun OptionButton(
    modifier: Modifier = Modifier,
    option: Option,
    isActive: Boolean,
    isEnable: Boolean,
    onClick: () -> Unit = {}
) {
    val contentColor = if (isActive) Color.Black else Color.White
    val backgroundColor = if (isActive) LightBlue else DarkGray

    Surface(
        modifier = modifier
            .size(width = 110.dp, height = 98.dp)
            .clickable(enabled = isEnable, onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(25.dp),
                painter = painterResource(id = option.iconId),
                contentDescription = "",
                tint = contentColor
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = stringResource(id = option.textId),
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
        option = Option.WINDOW_LOCK,
        isActive = true,
        isEnable = true
    )
}

@Preview
@Composable
fun DisabledOptionButtonPreview() {
    OptionButton(
        option = Option.WINDOW_LOCK,
        isActive = false,
        isEnable = false
    )
}