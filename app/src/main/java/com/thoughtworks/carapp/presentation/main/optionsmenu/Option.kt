package com.thoughtworks.carapp.presentation.main.optionsmenu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thoughtworks.carapp.R

enum class Option(
    @DrawableRes val iconId: Int,
    @StringRes val textId: Int
) {
    WINDOW_LOCK(R.drawable.ic_option_window_lock, R.string.option_window_lock),
    FRAGRANCE(R.drawable.ic_option_fragrance, R.string.option_fragrance),
    WI_FI(R.drawable.ic_option_wifi, R.string.option_wi_fi),
    BLUETOOTH(R.drawable.ic_option_bluetooth, R.string.option_bluetooth),
    CELLULAR(R.drawable.ic_option_cellular, R.string.option_cellular)
}