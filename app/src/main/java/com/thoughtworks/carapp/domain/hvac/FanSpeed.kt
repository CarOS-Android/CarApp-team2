package com.thoughtworks.carapp.domain.hvac

private const val SPEED_VALUE_OFF = 1
private const val SPEED_VALUE_1 = 2
private const val SPEED_VALUE_2 = 3
private const val SPEED_VALUE_3 = 4
private const val SPEED_VALUE_4 = 5

enum class FanSpeed(val value: Int) {
    OFF(SPEED_VALUE_OFF),
    SPEED_1(SPEED_VALUE_1),
    SPEED_2(SPEED_VALUE_2),
    SPEED_3(SPEED_VALUE_3),
    SPEED_MAX(SPEED_VALUE_4)
}