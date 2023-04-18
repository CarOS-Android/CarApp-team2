package com.thoughtworks.carapp.presentation.carsetting.hvacoptions

import androidx.annotation.DrawableRes
import com.thoughtworks.carapp.R

enum class HvacOptions(@DrawableRes val imageId: Int) {
    REAR_DEFROSTER(R.drawable.ic_hvac_options_rear_deforest),
    PLACEHOLDER(R.drawable.ic_hvac_options_placeholdert),
    INNER_RECIRCULATION(R.drawable.ic_hvac_options_inner_recirculation),
    OUT_RECIRCULATION(R.drawable.ic_hvac_options_out_recirculation),
    SIDE_MIRROR_HEAT(R.drawable.ic_hvac_options_side_mirror_heat),
    FRONT_DEFROSTER(R.drawable.ic_hvac_options_front_deforest),
}