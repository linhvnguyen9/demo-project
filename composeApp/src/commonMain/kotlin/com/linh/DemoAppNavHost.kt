package com.linh

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.linh.core.navigation.Users
import com.linh.features.users.presentation.navigation.usersNavGraph

@Composable
fun DemoAppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Users
    ) {
        usersNavGraph(navHostController) {
            navHostController.popBackStack()
        }
    }
}