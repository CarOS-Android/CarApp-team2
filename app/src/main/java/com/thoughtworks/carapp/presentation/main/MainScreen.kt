package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MainScreen() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        AutoHoldButton()
    }
}
