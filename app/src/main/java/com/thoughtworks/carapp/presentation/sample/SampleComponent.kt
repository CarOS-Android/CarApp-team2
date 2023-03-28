package com.thoughtworks.carapp.presentation.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SampleComponent(
    viewModel: SampleViewModel = viewModel()
) {

    val sampleState = viewModel.sampleState.collectAsState().value
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { viewModel.sendAction(SampleAction.GetData) }) {
            Text(text = "Get data")
        }

        when (sampleState) {
            is SampleState.Loading -> {
                CircularProgressIndicator()
            }
            is SampleState.Success -> {
                Text(text = sampleState.data)
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    MaterialTheme {
        SampleComponent()
    }
}
