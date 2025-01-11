package com.linh.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.Dp

data class DemoAppSpacing(
    val small: Dp = SpacingDefaults.small,
    val default: Dp = SpacingDefaults.default
)