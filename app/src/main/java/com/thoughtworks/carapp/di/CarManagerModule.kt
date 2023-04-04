package com.thoughtworks.carapp.di

import android.car.Car
import android.car.hardware.property.CarPropertyManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CarManagerModule {

    @Provides
    fun provideCarPropertyManager(car: Car): CarPropertyManager =
        car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager

    @Provides
    fun provideCar(
        @ApplicationContext context: Context
    ): Car = Car.createCar(context)
}
