package com.thoughtworks.carapp.presentation.start

import androidx.annotation.DrawableRes
import com.thoughtworks.carapp.R

enum class StartDestination(
    @DrawableRes val icon: Int
) {
    CarSetting(R.drawable.ic_car_setting),
    Navigation(R.drawable.ic_navigation),
    Main(R.drawable.ic_main),
    Music(R.drawable.ic_music),
    Setting(R.drawable.ic_setting)
}

val startDestinations: List<StartDestination> = StartDestination.values().asList()
