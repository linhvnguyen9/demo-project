package com.linh.core.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import demoproject.core.ui.generated.resources.Res
import demoproject.core.ui.generated.resources.all_back_content_description
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoAppTopAppBar(
    title: String,
    onNavigateBack: () -> Unit,
    isBackEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.h5) },
        navigationIcon = {
            if (isBackEnabled) {
                IconButton(onNavigateBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        // Set content description so users using screen readers can know what this button does
                        contentDescription = stringResource(Res.string.all_back_content_description)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
    )
}