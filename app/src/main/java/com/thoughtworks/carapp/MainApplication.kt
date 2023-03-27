@file:Suppress("PackageNaming")

package com.thoughtworks.carapp

import android.app.Application
import com.thoughtworks.ark.router.Router
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Router.enableLog(BuildConfig.DEBUG)
    }
}