package com.thoughtworks.carapp.domain.model

enum class SeatFuncGear(val value: Int) {
    GEAR_OFF(0),
    GEAR_1(1),
    GEAR_2(2);

    fun getNextGear(): SeatFuncGear {
        val currentIndex = values().indexOf(this)
        return if (currentIndex == values().lastIndex) {
            GEAR_OFF
        } else {
            values()[currentIndex + 1]
        }
    }
}