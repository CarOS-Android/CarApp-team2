package com.thoughtworks.carapp.presentation.carsetting.fragrance

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.blindhmi.ui.component.item.Item
import com.thoughtworks.blindhmi.ui.composable.ItemDsl
import com.thoughtworks.blindhmi.ui.composable.border
import com.thoughtworks.blindhmi.ui.composable.center
import com.thoughtworks.blindhmi.ui.composable.indicator
import com.thoughtworks.blindhmi.ui.composable.item
import com.thoughtworks.blindhmi.ui.composable.radio.ComposeBlindHMIRadioGroup
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.presentation.carsetting.ac.AcViewModel
import com.thoughtworks.carapp.presentation.carsetting.disabled
import com.thoughtworks.carapp.presentation.carsetting.gesturesDisabled

private const val WHOLE_ANGLE = 360f

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun FragranceController(
    modifier: Modifier = Modifier,
    viewModel: FragranceViewModel,
    acViewModel: AcViewModel = viewModel()
) {
    val driverState by viewModel.driverState.collectAsState()
    val copilotState by viewModel.copilotState.collectAsState()
    val backSeatState by viewModel.backSeatState.collectAsState()

    val isAcPowerOn by acViewModel.isAcPowerOn.collectAsState()
    val isFragranceOn by viewModel.isFragranceOn.collectAsState()

    val disabled = if (!isAcPowerOn) true else !isFragranceOn

    Box(
        modifier = modifier
            .padding(top = 120.dp, end = 98.dp)
            .size(width = 535.dp, height = 573.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_car_inner),
            contentDescription = ""
        )
        Image(
            painter = painterResource(id = R.drawable.img_fragrance_pointer),
            contentDescription = "",
            modifier = Modifier.padding(top = 86.dp, start = 68.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 30.dp)
        ) {
            FragranceButton(disabled, driverState) {
                viewModel.sendEvent(FragranceEvent.DriverFragranceEvent(it.getLabel()))
            }
            Spacer(modifier = Modifier.height(24.dp))
            FragranceButton(disabled, copilotState) {
                viewModel.sendEvent(FragranceEvent.CopilotFragranceEvent(it.getLabel()))
            }
            Spacer(modifier = Modifier.height(24.dp))
            FragranceButton(disabled, backSeatState) {
                viewModel.sendEvent(FragranceEvent.BackSeatFragranceEvent(it.getLabel()))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun FragranceButton(disabled: Boolean, currentSelection: FragranceOptions, onItemSelected: (Item) -> Unit) {
    val context = LocalContext.current
    val borderRadiusPx = with(LocalDensity.current) { 72.dp.roundToPx() }
    val indicatorRadiusPx = with(LocalDensity.current) { 45.dp.roundToPx() }
    val centerRadiusPx = with(LocalDensity.current) { 9.dp.roundToPx() }

    var selectedOption by remember { mutableStateOf("") }

    ComposeBlindHMIRadioGroup(
        modifier = Modifier
            .size(144.dp)
            .disabled(disabled)
            .gesturesDisabled(disabled),
        centerBackgroundRadius = 83.dp,
        centerBackgroundRes = R.drawable.ic_fragrance_button_center_bg,
        layoutRadius = 36.dp,
        border = {
            border(context) {
                imageRes = R.drawable.ic_fragrance_button_bg
                radius = borderRadiusPx
                drawOrder = getDrawOrder() - 1
            }
        },
        indicator = {
            indicator(context) {
                imageRes = R.drawable.ic_blind_hmi_indicator
                radius = indicatorRadiusPx
                drawOrder = getDrawOrder() + 1
                onActive = { setVisible(true) }
                onInActive = { setVisible(false) }
            }
        },
        center = {
            center(context) {
                imageRes = R.drawable.ic_fragrance_button_center
                radius = centerRadiusPx
                drawOrder = getDrawOrder() + 2
            }
        },
        items = createItems(context),
        onItemSelected = onItemSelected,
        currentSelectedItemLabel = selectedOption
    )

    LaunchedEffect(currentSelection) {
        selectedOption = currentSelection.name
    }
}

private fun createItems(context: Context): List<Item> {
    val itemsList = mutableListOf<Item>()
    val angleEachItem = WHOLE_ANGLE / FragranceOptions.optionList.size
    val startAngle = angleEachItem / 2
    FragranceOptions.optionList.forEachIndexed { index, fragrance ->
        val item = item(context) {
            imageRes = fragrance.imgRes
            angle = startAngle + index * angleEachItem
            label = fragrance.name
            checked = false
            span = angleEachItem
            size = context.resources.getDimensionPixelSize(R.dimen.fragrance_option_item_size)
            itemType = ItemDsl.ItemType.RADIO_GROUP
        }
        itemsList.add(item)
    }
    return itemsList
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview
@Composable
fun FragranceButtonPreview() {
    FragranceButton(false, FragranceOptions.SECRET) {}
}