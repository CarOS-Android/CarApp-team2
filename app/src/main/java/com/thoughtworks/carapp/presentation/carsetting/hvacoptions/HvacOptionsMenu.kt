package com.thoughtworks.carapp.presentation.carsetting.hvacoptions

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.blindhmi.ui.component.item.Item
import com.thoughtworks.blindhmi.ui.composable.center
import com.thoughtworks.blindhmi.ui.composable.checkbox.ComposeBlindHMICheckBoxGroup
import com.thoughtworks.blindhmi.ui.composable.indicator
import com.thoughtworks.blindhmi.ui.composable.item
import com.thoughtworks.carapp.R

private const val WHOLE_ANGLE = 360f
private const val OPTION_ITEM_LAYOUT_SPAN = 32f

@Composable
fun HvacOptionsMenu() {
    val context = LocalContext.current
    val indicatorRadiusPx = with(LocalDensity.current) { 60.dp.roundToPx() }

    ComposeBlindHMICheckBoxGroup(
        modifier = Modifier
            .padding(top = 423.dp, start = 630.dp)
            .size(width = 140.dp, height = 153.dp),
        centerBackgroundRadius = 70.dp,
        centerBackgroundRes = R.drawable.ic_hvac_options_border,
        layoutRadius = 44.dp,
        indicator = {
            indicator(context) {
                imageRes = R.drawable.ic_menu_indicator
                drawOrder = getDrawOrder() + 1
                radius = indicatorRadiusPx
                onActive = { setVisible(true) }
                onInActive = { setVisible(false) }
            }
        },
        center = {
            center(context) {
                contentFactory = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_hvac_options_center),
                        contentDescription = ""
                    )
                }
                drawOrder = getDrawOrder() + 2
            }
        },
        items = createItems(context),
        onItemSelected = {
            it.getLabel()
        }
    )
}

private fun createItems(context: Context): List<Item> {
    val itemsList = mutableListOf<Item>()
    val angleOfEachItem = WHOLE_ANGLE / HvacOptions.values().size
    val startAngle = angleOfEachItem / 2
    HvacOptions.values().forEachIndexed { index, option ->
        val item = item(context) {
            angle = startAngle + index * angleOfEachItem
            span = OPTION_ITEM_LAYOUT_SPAN
            imageRes = option.imageId
            label = option.name
            size = context.resources.getDimensionPixelSize(R.dimen.hvac_option_menu_item_size)
            checked = false
        }
        itemsList.add(item)
    }

    return itemsList
}
