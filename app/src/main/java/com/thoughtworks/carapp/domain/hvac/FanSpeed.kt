package com.thoughtworks.carapp.domain.hvac

enum class FanSpeed(val value: Int) {
    OFF(1),
    SPEED_MIN(2),
    SPEED_2(3),
    SPEED_3(4),
    SPEED_MAX(5)
}