package com.thoughtworks.carapp.presentation.carsetting.fragrance

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.thoughtworks.blindhmi.ui.component.item.Item
import com.thoughtworks.blindhmi.ui.composable.ItemDsl
import com.thoughtworks.blindhmi.ui.composable.border
import com.thoughtworks.blindhmi.ui.composable.center
import com.thoughtworks.blindhmi.ui.composable.indicator
import com.thoughtworks.blindhmi.ui.composable.item
import com.thoughtworks.blindhmi.ui.composable.radio.ComposeBlindHMIRadioGroup
import com.thoughtworks.carapp.R

private const val WHOLE_ANGLE = 360f

@Composable
fun FragranceController(modifier: Modifier) {
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
        FragranceButtonGroups(Modifier.align(Alignment.TopEnd))
    }
}

@Composable
private fun FragranceButtonGroups(modifier: Modifier) {
    var driverState by remember { mutableStateOf(FragranceOptions.SECRET) }
    var copilotState by remember { mutableStateOf(FragranceOptions.SUNSHINE) }
    var backSeatState by remember { mutableStateOf(FragranceOptions.STAR) }

    Column(modifier = modifier.padding(top = 30.dp)) {
        FragranceButton(driverState) { item ->
            Log.i("ComposeBlindHMIRadioGroup", "driverState: ${item.getLabel()}")
            FragranceOptions.getOptionByName(item.getLabel())?.let { driverState = it }
        }
        Spacer(modifier = Modifier.height(24.dp))
        FragranceButton(copilotState) { item ->
            Log.i("ComposeBlindHMIRadioGroup", "copilotState: ${item.getLabel()}")
            FragranceOptions.getOptionByName(item.getLabel())?.let { copilotState = it }
        }
        Spacer(modifier = Modifier.height(24.dp))
        FragranceButton(backSeatState) { item ->
            Log.i("ComposeBlindHMIRadioGroup", "backSeatState: ${item.getLabel()}")
            FragranceOptions.getOptionByName(item.getLabel())?.let { backSeatState = it }
        }
    }
}

@Composable
private fun FragranceButton(currentSelection: FragranceOptions, onItemSelected: (Item) -> Unit) {
    val context = LocalContext.current
    val borderRadiusPx = with(LocalDensity.current) { 72.dp.roundToPx() }
    val indicatorRadiusPx = with(LocalDensity.current) { 45.dp.roundToPx() }
    val centerRadiusPx = with(LocalDensity.current) { 9.dp.roundToPx() }

    var selectedOption by remember { mutableStateOf("") }

    ComposeBlindHMIRadioGroup(
        modifier = Modifier.size(144.dp),
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
    val angleEachItem = WHOLE_ANGLE / FragranceOptions.values().size
    val startAngle = angleEachItem / 2
    FragranceOptions.values().forEachIndexed { index, fragrance ->
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

@Preview
@Composable
fun FragranceButtonPreview() {
    FragranceButton(FragranceOptions.SECRET) {}
}