package com.linh.features.users.presentation.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.linh.core.ui.DemoAppTopAppBar
import com.linh.core.ui.theme.spacing
import demoproject.features.users.generated.resources.Res
import demoproject.features.users.generated.resources.users_list_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun UsersListScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onTapUserDetail: (username: String) -> Unit
) {
    Scaffold(
        topBar = {
            DemoAppTopAppBar(
                title = stringResource(Res.string.users_list_title),
                onNavigateBack = onBackPressed
            )
        }
    ) { contentPadding ->
        LazyColumn(
            Modifier.padding(contentPadding),
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.default)
        ) {
            item {
                Text("Hello from Users List screen")
                Button(onClick = { onTapUserDetail("linh") }) {
                    Text("Go to User Detail")
                }
            }
        }
    }
}