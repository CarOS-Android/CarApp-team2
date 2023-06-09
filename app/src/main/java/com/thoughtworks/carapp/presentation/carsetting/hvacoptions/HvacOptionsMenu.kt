package com.thoughtworks.carapp.presentation.carsetting.hvacoptions

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.blindhmi.ui.component.item.Item
import com.thoughtworks.blindhmi.ui.composable.ItemDsl
import com.thoughtworks.blindhmi.ui.composable.center
import com.thoughtworks.blindhmi.ui.composable.checkbox.ComposeBlindHMICheckBoxGroup
import com.thoughtworks.blindhmi.ui.composable.indicator
import com.thoughtworks.blindhmi.ui.composable.item
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.presentation.carsetting.disabled
import com.thoughtworks.carapp.presentation.carsetting.gesturesDisabled

private const val WHOLE_ANGLE = 360f
private const val OPTION_ITEM_LAYOUT_SPAN = 32f

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HvacOptionsMenu(
    isAcPowerOn: Boolean,
    isAcAutoOn: Boolean,
    viewModel: HvacOptionsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val indicatorRadiusPx = with(LocalDensity.current) { 60.dp.roundToPx() }

    val disabled = if (!isAcPowerOn) true else isAcAutoOn

    ComposeBlindHMICheckBoxGroup(
        modifier = Modifier
            .padding(top = 423.dp, start = 630.dp)
            .size(width = 140.dp, height = 153.dp)
            .disabled(disabled)
            .gesturesDisabled(disabled),
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
        checkStateList = uiState,
        onItemSelected = {
            viewModel.sendEvent(HvacOptionsEvent.OnHvacOptionSelected(it.getLabel()))
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
            itemType = ItemDsl.ItemType.CHECK_BOX
        }
        itemsList.add(item)
    }

    return itemsList
}
