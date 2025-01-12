package com.linh.features.users.presentation.navigation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.linh.core.navigation.Users
import com.linh.features.users.presentation.detail.UserDetailScreen
import com.linh.features.users.presentation.detail.UserDetailViewModel
import com.linh.features.users.presentation.list.UsersListScreen
import com.linh.features.users.presentation.list.UsersListViewModel
import io.ktor.http.parameters
import io.ktor.http.parametersOf
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data class UserDetail(val username: String)

@Serializable
data object UsersList

fun NavGraphBuilder.usersNavGraph(navController: NavController, onNavigateBack: () -> Unit) {
    navigation<Users>(startDestination = UsersList) {
        composable<UsersList> {
            val viewModel = koinViewModel<UsersListViewModel>()

            UsersListScreen(
                viewModel.uiState.collectAsStateWithLifecycle().value,
                onBackPressed = {
                    onNavigateBack()
                },
                onTapUserDetail = {
                    navController.navigate(UserDetail(it))
                }
            )
        }
        composable<UserDetail> {
            val navArgs = it.toRoute<UserDetail>()

            val viewModel =
                koinViewModel<UserDetailViewModel>(parameters = { parametersOf(navArgs.username) } )

            UserDetailScreen(
                viewModel.uiState.collectAsStateWithLifecycle().value,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}