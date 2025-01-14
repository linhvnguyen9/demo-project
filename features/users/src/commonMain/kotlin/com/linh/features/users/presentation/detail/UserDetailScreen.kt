package com.linh.features.users.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.linh.core.domain.model.user.User
import com.linh.core.ui.components.DemoAppTopAppBar
import com.linh.core.ui.theme.spacing
import com.linh.features.users.presentation.ui.UserCard
import demoproject.features.users.generated.resources.Res
import demoproject.features.users.generated.resources.ic_group
import demoproject.features.users.generated.resources.ic_medal
import demoproject.features.users.generated.resources.users_detail_blog
import demoproject.features.users.generated.resources.users_detail_follower
import demoproject.features.users.generated.resources.users_detail_following
import demoproject.features.users.generated.resources.users_detail_location_content_description
import demoproject.features.users.generated.resources.users_detail_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import demoproject.features.users.generated.resources.users_detail_error_fetching_latest
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

@Composable
internal fun UserDetailScreen(
    uiState: UserDetailUiState,
    onBackPressed: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState) {
        if (uiState is UserDetailUiState.Data && uiState.remoteFetchError) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    getString(Res.string.users_detail_error_fetching_latest),
                    duration = SnackbarDuration.Indefinite
                )
            }
        }
    }

    Scaffold(
        topBar = {
            DemoAppTopAppBar(
                title = stringResource(Res.string.users_detail_title),
                onNavigateBack = onBackPressed
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { contentPadding ->
        when (uiState) {
            is UserDetailUiState.Data -> {
                UserDetailDataState(contentPadding, uiState)
            }

            is UserDetailUiState.NoCachedDataLoadError -> {
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
private fun UserDetailDataState(
    contentPadding: PaddingValues,
    uiState: UserDetailUiState.Data
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        Column(
            Modifier
                .padding(horizontal = MaterialTheme.spacing.default)
        ) {
            UserDetailCard(uiState.user)
            Spacer(Modifier.height(MaterialTheme.spacing.medium))
            UserDetailInfoSection(
                followersCount = uiState.user?.followers?.toString().orEmpty(),
                followingCount = uiState.user?.following?.toString().orEmpty()
            )
            Text(
                text = stringResource(Res.string.users_detail_blog),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(MaterialTheme.spacing.default))
            Text(
                text = uiState.user?.profileUrl.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        if (uiState.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun UserDetailCard(user: User?) {
    UserCard(
        title = user?.login.orEmpty(),
        avatarUrl = user?.avatarUrl.orEmpty(),
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
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

@Composable
fun UserDetailInfoSection(
    followersCount: String,
    followingCount: String
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        InfoItem(
            icon = painterResource(Res.drawable.ic_group),
            count = followersCount,
            label = stringResource(Res.string.users_detail_follower)
        )
        InfoItem(
            icon = painterResource(Res.drawable.ic_medal),
            count = followingCount,
            label = stringResource(Res.string.users_detail_following)
        )
    }
}

@Composable
fun InfoItem(icon: Painter, count: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color(0xFFF3F4F5), shape = CircleShape)
                .padding(MaterialTheme.spacing.tiny)
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(28.dp).align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = count,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Text(text = label, style = MaterialTheme.typography.bodySmall)
    }
}