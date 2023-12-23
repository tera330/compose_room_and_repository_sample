package com.example.composeroomsample

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeroomsample.ui.theme.navigation.AppNavHost

@Composable
fun TaskApp(navController: NavHostController = rememberNavController()) {
    AppNavHost(navController = navController)
}