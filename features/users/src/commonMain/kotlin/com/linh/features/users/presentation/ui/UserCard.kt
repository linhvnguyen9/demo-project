package com.linh.features.users.presentation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UserCard(onClick: () -> Unit, clickEnabled: Boolean = true, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        onClick = onClick,
        enabled = clickEnabled
    ) {
        Row(Modifier.fillMaxWidth().padding()) {

        }
    }
}