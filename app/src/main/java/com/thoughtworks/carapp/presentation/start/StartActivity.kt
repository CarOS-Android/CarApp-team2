@file:Suppress("PackageNaming")

package com.thoughtworks.carapp.presentation.start

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.presentation.carsetting.CarSettingScreen
import com.thoughtworks.carapp.presentation.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("DEPRECATION")
class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideNavigationBar()
        setContent {
            var currentDestination by remember { mutableStateOf(StartDestination.Main) }
            Row {
                Column(
                    modifier = Modifier.background(colorResource(id = R.color.second_primary))
                        .wrapContentWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.padding(vertical = 26.dp),
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = ""
                    )
                    SideNavigationBar(currentDestination) { currentDestination = it }
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    when (currentDestination) {
                        StartDestination.Main -> MainScreen()
                        StartDestination.CarSetting -> CarSettingScreen()
                        else -> PlaceHolderScreen(currentDestination.name)
                    }
                }
            }
        }
    }

    @Composable
    private fun PlaceHolderScreen(name: String) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(name.plus("Screen"), color = Color.White)
        }
    }

    @SuppressWarnings("kotlin:S1874")
    private fun hideNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.navigationBars())
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }
}
