package com.linh.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

val LocalSpacing = compositionLocalOf { DemoAppSpacing() }

@Composable
fun DemoAppTheme(
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(
        LocalSpacing provides DemoAppSpacing() // We define CompositionLocal for spacing so that screens can read this and have consistent sizing
    ) {
        MaterialTheme {
            content()
        }
    }
}

val MaterialTheme.spacing: DemoAppSpacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current