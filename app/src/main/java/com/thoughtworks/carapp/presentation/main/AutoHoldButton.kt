package com.thoughtworks.carapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R

@Composable
fun AutoHoldButton() {
    var isSelected by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier.selectable(
            selected = isSelected,
            onClick = {
                isSelected = !isSelected
            },
            enabled = true,
            role = Role.Image
        ).wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_auto_hold_bg),
            contentDescription = ""
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_auto_hold_text),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.height(15.dp))
            Image(
                painter = painterResource(
                    id = if (isSelected) {
                        R.drawable.ic_auto_hold_indicator_opened
                    } else {
                        R.drawable.ic_auto_hold_indicator_closed
                    }
                ),
                contentDescription = ""
            )
        }
    }
}
