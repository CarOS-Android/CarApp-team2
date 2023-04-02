@file:Suppress("PackageNaming")

package com.thoughtworks.carapp.presentation

import android.car.VehicleAreaType
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // move into ViewModel, just for test here
    @Inject
    lateinit var carPropertyManager: CarPropertyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*
                CarService:
                public static final int CURRENT_GEAR = 289408001
                (((0x0401 | VehiclePropertyGroup:SYSTEM) | VehiclePropertyType:INT32) | VehicleArea:GLOBAL)

                or Hardware:
                CURRENT_GEAR = (
                    0x0401
                    | VehiclePropertyGroup:SYSTEM
                    | VehiclePropertyType:INT32
                    | VehicleArea:GLOBAL),
             */

            val currentGear = carPropertyManager.getProperty<Int>(
                VehiclePropertyIds.CURRENT_GEAR,
                VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL
            ).value

            val currentGearCallback = object : CarPropertyManager.CarPropertyEventCallback{
                override fun onChangeEvent(p0: CarPropertyValue<*>?) {
                    Log.i("currentGearCallback", "Current GEAR: ${p0?.value}")
                }

                override fun onErrorEvent(p0: Int, p1: Int) {
                    Log.e("currentGearCallback", "Get Current GEAR ERROR!")
                }

            }
            carPropertyManager.registerCallback(currentGearCallback, VehiclePropertyIds.CURRENT_GEAR, 10f)

            // wrong param type
            // val currentGearString = carPropertyManager.getProperty<String>(
            //   VehiclePropertyIds.CURRENT_GEAR,
            //   VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL
            // ).value

            Log.i("carPropertyManager", "Current GEAR: $currentGear")
        }
    }
}
