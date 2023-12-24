package com.example.composeroomsample.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeroomsample.ui.theme.screen.HomeScreen
import com.example.composeroomsample.ui.theme.screen.TaskEntryScreen

enum class Screen {
    Home,
    InsertToTask,
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // val navController = rememberNavController()

   NavHost(
       navController = navController,
       startDestination = Screen.Home.name
   ) {
       composable(route = Screen.Home.name) {
           HomeScreen(
               navigateToTaskEntry = {
                   navController.navigate(Screen.InsertToTask.name)
               }
           )
       }
       composable(route = Screen.InsertToTask.name) {
           TaskEntryScreen(
               navigateToHome = {
                   navController.navigate(Screen.Home.name)
               }
           )
       }
   }
}