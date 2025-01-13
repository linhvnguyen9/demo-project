package com.linh.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.linh.core.ui.theme.spacing
import demoproject.core.ui.generated.resources.Res
import demoproject.core.ui.generated.resources.all_unknown_error
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorText(message: String?, modifier: Modifier = Modifier) {
    Text(
        text = message ?: stringResource(Res.string.all_unknown_error),
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Red
    )
}