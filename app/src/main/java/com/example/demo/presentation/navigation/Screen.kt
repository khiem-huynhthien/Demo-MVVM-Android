package com.example.demo.presentation.navigation

sealed class Screen(val route: String) {
    data object UserList : Screen("user_list")
    data object UserDetails : Screen("user_details/{username}") {
        fun createRoute(username: String) = "user_details/$username"
    }
}