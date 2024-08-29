package com.example.demo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.demo.presentation.ui.screen.MainScreen
import com.example.demo.presentation.ui.screen.UserDetailScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.UserList.route) {
        composable(Screen.UserList.route) {
            MainScreen(navController = navController)
        }
        composable(Screen.UserDetails.route) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            UserDetailScreen(username = username ?: "", navController = navController)
        }
    }
}
