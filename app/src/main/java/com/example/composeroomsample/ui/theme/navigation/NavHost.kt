package com.example.composeroomsample.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.composeroomsample.ui.theme.screen.HomeScreen
import com.example.composeroomsample.ui.theme.screen.TaskDetailScreen
import com.example.composeroomsample.ui.theme.screen.TaskEditScreen
import com.example.composeroomsample.ui.theme.screen.TaskEntryScreen

object Screen {
    val home = "Home"
    val insertToTask = "InsertToTask"
    val taskDetail = "TaskDetail"
    val taskEdit = "TaskEdit"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
   NavHost(
       navController = navController,
       startDestination = Screen.home
   ) {
       composable(route = Screen.home) {
           HomeScreen(
               navigateToTaskEntry = {
                   navController.navigate(Screen.insertToTask)
               },
               navigateToTaskDetail = { taskId ->
                   navController.navigate("${Screen.taskDetail}/$taskId")
               }
           )
       }
       composable(
           route = Screen.insertToTask
       ) {
           TaskEntryScreen(
               navigateToHome = {
                   navController.navigate(Screen.home)
               }
           )
       }
       composable(
           route = "${Screen.taskDetail}/{taskId}",
           arguments = listOf(navArgument("taskId") { type = NavType.IntType })
       ) { backStackEntry ->
           val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
           TaskDetailScreen(
               taskId,
               navigateBack = { navController.navigateUp() },

               navigateToTaskEdit = {
                   navController.navigate("${Screen.taskEdit}/$taskId")
               }
           )
       }
       composable(
           route ="${Screen.taskEdit}/{taskId}",
           arguments = listOf(navArgument("taskId") { type = NavType.IntType })
       ) { backStackEntry ->
           val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
           TaskEditScreen(
               onSaveClick = { navController.navigate(Screen.home) },
               taskId = taskId
           )
       }
   }
}