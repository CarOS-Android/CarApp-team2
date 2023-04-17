package com.thoughtworks.carapp.presentation.carsetting.fragrance

import androidx.annotation.DrawableRes
import com.thoughtworks.carapp.R

enum class FragranceOptions(@DrawableRes val imgRes: Int) {
    STAR(R.drawable.ic_fragrance_option_star),
    SUNSHINE(R.drawable.ic_fragrance_option_sunshine),
    SECRET(R.drawable.ic_fragrance_option_secret);

    companion object {
        fun getOptionByName(name: String): FragranceOptions? = values().find { it.name == name }
    }
}