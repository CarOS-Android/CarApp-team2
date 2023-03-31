@file:Suppress("PackageNaming")

package com.thoughtworks.carapp.presentation

import android.car.VehicleAreaType
import android.car.VehiclePropertyIds
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

            // wrong param type
            // val currentGearString = carPropertyManager.getProperty<String>(
            //   VehiclePropertyIds.CURRENT_GEAR,
            //   VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL
            // ).value

            Log.i("carPropertyManager", "Current GEAR: $currentGear")
        }
    }
}
