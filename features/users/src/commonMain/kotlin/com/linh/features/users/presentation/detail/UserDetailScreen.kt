package com.linh.features.users.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.linh.core.ui.DemoAppTopAppBar
import com.linh.core.ui.theme.spacing
import demoproject.features.users.generated.resources.Res
import demoproject.features.users.generated.resources.users_detail_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun UserDetailScreen(modifier: Modifier = Modifier, onBackPressed: () -> Unit) {
    Scaffold(
        topBar = {
            DemoAppTopAppBar(
                title = stringResource(Res.string.users_detail_title),
                onNavigateBack = onBackPressed
            )
        }
    ) { contentPadding ->
        Column(Modifier.fillMaxSize().padding(horizontal = MaterialTheme.spacing.default)) {
            Text("User detail")
        }
    }
}