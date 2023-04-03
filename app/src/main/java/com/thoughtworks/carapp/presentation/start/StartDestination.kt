package com.thoughtworks.carapp.presentation.start

import androidx.annotation.DrawableRes
import com.thoughtworks.carapp.R

enum class StartDestination(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
) {
    CarSetting(R.drawable.ic_car_setting_selected, R.drawable.ic_car_setting_unselected),
    Navigation(R.drawable.ic_navigation_selected, R.drawable.ic_navigation_unselected),
    Main(R.drawable.ic_main_selected, R.drawable.ic_main_unselected),
    Music(R.drawable.ic_music_selected, R.drawable.ic_music_unselected),
    Setting(R.drawable.ic_setting_selected, R.drawable.ic_setting_unselected)
}

val startDestinations: List<StartDestination> = StartDestination.values().asList()
