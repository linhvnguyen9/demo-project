package com.linh.features.users.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import app.cash.paging.LoadStateError
import app.cash.paging.PagingData
import com.linh.core.domain.model.user.User
import com.linh.core.ui.components.DemoAppTopAppBar
import com.linh.core.ui.theme.spacing
import com.linh.features.users.presentation.ui.UserCard
import demoproject.features.users.generated.resources.Res
import demoproject.core.ui.generated.resources.Res as CommonUiRes
import demoproject.features.users.generated.resources.users_list_title
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.stringResource
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.linh.core.ui.components.ErrorText
import demoproject.core.ui.generated.resources.all_retry
import demoproject.core.ui.generated.resources.all_unknown_error
import demoproject.features.users.generated.resources.users_list_end_of_list

@Composable
internal fun UsersListScreen(
    usersList: Flow<PagingData<User>>,
    onBackPressed: () -> Unit,
    onTapUserDetail: (username: String) -> Unit
) {
    Scaffold(
        topBar = {
            DemoAppTopAppBar(
                title = stringResource(Res.string.users_list_title),
                onNavigateBack = onBackPressed,
                isBackEnabled = false
            )
        }
    ) { contentPadding ->
        val items: LazyPagingItems<User> = usersList.collectAsLazyPagingItems()

        Box(Modifier.fillMaxSize()) {
            if (items.loadState.refresh is LoadStateError && items.itemCount == 0) {
                ErrorState(
                    message = (items.loadState.refresh as LoadStateError).error.message,
                    onRetry = {
                        items.retry()
                    }
                )
            } else {
                LazyColumn(
                    Modifier.padding(contentPadding),
                    contentPadding = PaddingValues(
                        horizontal = MaterialTheme.spacing.default,
                        vertical = MaterialTheme.spacing.small
                    ),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    items(
                        items.itemCount,
                        key = {
                            items[it]?.id ?: it
                        }
                    ) { index ->
                        val currentItem = items[index]

                        currentItem?.let {
                            UserCard(
                                title = currentItem.login,
                                avatarUrl = currentItem.avatarUrl,
                                content = {
                                    Text(
                                        text = currentItem.profileUrl,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                onClick = { onTapUserDetail(it.login) },
                            )
                        }
                    }
                    when (val loadState = items.loadState.append) {
                        is LoadState.Error -> item {
                            ErrorText(loadState.error.message, modifier = Modifier.fillMaxWidth().align(Alignment.Center))
                        }
                        LoadState.Loading -> {
                            item {
                                Column(Modifier.fillMaxWidth()) {
                                    CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                                }
                            }
                        }
                        is LoadState.NotLoading -> {
                            if (loadState.endOfPaginationReached) {
                                item {
                                    Text(stringResource(Res.string.users_list_end_of_list), modifier = Modifier.fillMaxWidth().align(Alignment.Center))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun BoxScope.ErrorState(
    message: String?,
    onRetry: () -> Unit
) {
    Column(
        Modifier.align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ErrorText(message)
        Spacer(Modifier.height(MaterialTheme.spacing.small))
        Button(onClick = onRetry) {
            Text(stringResource(CommonUiRes.string.all_retry))
        }
    }
}