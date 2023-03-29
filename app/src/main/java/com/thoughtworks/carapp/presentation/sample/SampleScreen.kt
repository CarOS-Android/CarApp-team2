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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SampleComponent(
    viewModel: SampleViewModel = viewModel()
) {
    val sampleState by viewModel.sampleState.collectAsState()
    val thumpUpState by viewModel.thumpUpState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (sampleState) {
            is SampleState.Loading -> {
                CircularProgressIndicator()
            }
            is SampleState.Success -> {
                Column {
                    Text(text = (sampleState as SampleState.Success).data)
                    Button(onClick = { viewModel.sendEvent(SampleEvent.ThumbsUpEvent) }) {
                        Text(text = if (thumpUpState is ThumpUpState.Unliked) "点赞" else "取消点赞")
                    }
                }
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
