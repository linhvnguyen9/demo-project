package com.linh.features.users.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.linh.core.ui.DemoAppTopAppBar
import com.linh.core.ui.theme.spacing
import com.linh.features.users.presentation.ui.UserCard
import demoproject.features.users.generated.resources.Res
import demoproject.features.users.generated.resources.users_list_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun UsersListScreen(
    uiState: UsersListUiState,
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
            contentPadding = PaddingValues(
                horizontal = MaterialTheme.spacing.default,
                vertical = MaterialTheme.spacing.small
            ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.tiny)
        ) {
            items(uiState.users) {
                UserCard(
                    title = it.login,
                    avatarUrl = it.avatarUrl,
                    content = {
                        Text(text = it.profileUrl, style = MaterialTheme.typography.bodyMedium)
                    },
                    onClick = { onTapUserDetail(it.login) },
                )
            }
        }
    }
}