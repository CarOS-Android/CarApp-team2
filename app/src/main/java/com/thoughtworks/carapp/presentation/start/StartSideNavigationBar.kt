package com.thoughtworks.carapp.presentation.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun SideNavigationBar(
    currentDestination: StartDestination,
    onSelected: (index: StartDestination) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentWidth(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        startDestinations.forEach { destination ->
            Icon(
                modifier = Modifier
                    .selectable(
                        selected = destination == currentDestination,
                        onClick = {
                            onSelected.invoke(destination)
                        },
                        enabled = true,
                        role = Role.Image
                    )
                    .padding(horizontal = 42.dp),
                painter = painterResource(id = destination.icon),
                contentDescription = "",
                tint = if (currentDestination == destination) Color.White else Color.Unspecified
            )
        }
    }
}
