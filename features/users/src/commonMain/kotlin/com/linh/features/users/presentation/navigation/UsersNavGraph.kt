package com.linh.features.users.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.linh.core.navigation.Users
import com.linh.features.users.presentation.detail.UserDetailScreen
import com.linh.features.users.presentation.list.UsersListScreen
import kotlinx.serialization.Serializable

@Serializable
data class UserDetail(val username: String)
@Serializable
data object UsersList

fun NavGraphBuilder.usersNavGraph(navController: NavController, onNavigateBack: () -> Unit) {
    navigation<Users>(startDestination = UsersList) {
        composable<UsersList> {
            UsersListScreen(
                onBackPressed = {
                    onNavigateBack()
                },
                onTapUserDetail = {
                    navController.navigate(UserDetail(it))
                }
            )
        }
        composable<UserDetail> {
            UserDetailScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}