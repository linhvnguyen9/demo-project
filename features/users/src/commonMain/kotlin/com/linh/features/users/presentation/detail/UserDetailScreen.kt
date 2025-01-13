package com.linh.features.users.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.linh.core.domain.model.user.User
import com.linh.core.ui.components.DemoAppTopAppBar
import com.linh.core.ui.theme.spacing
import com.linh.features.users.presentation.ui.UserCard
import demoproject.features.users.generated.resources.Res
import demoproject.features.users.generated.resources.users_detail_location_content_description
import demoproject.features.users.generated.resources.users_detail_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun UserDetailScreen(
    uiState: UserDetailUiState,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            DemoAppTopAppBar(
                title = stringResource(Res.string.users_detail_title),
                onNavigateBack = onBackPressed
            )
        }
    ) { contentPadding ->
        when (uiState) {
            is UserDetailUiState.Data -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .padding(horizontal = MaterialTheme.spacing.default)
                ) {
                    UserDetailCard(uiState.user)
                }
            }

            is UserDetailUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = uiState.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun UserDetailCard(user: User?) {
    UserCard(
        title = user?.name.orEmpty(),
        avatarUrl = user?.avatarUrl.orEmpty(),
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = stringResource(Res.string.users_detail_location_content_description)
                )
                Spacer(Modifier.width(MaterialTheme.spacing.tiny))
                Text(
                    text = user?.location.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    )
}