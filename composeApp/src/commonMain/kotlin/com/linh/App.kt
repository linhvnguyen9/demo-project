package com.linh

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.linh.core.ui.theme.DemoAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    DemoAppTheme {
        val navController = rememberNavController()

        Column(Modifier.fillMaxSize()) {
            DemoAppNavHost(navController)
        }
    }
}