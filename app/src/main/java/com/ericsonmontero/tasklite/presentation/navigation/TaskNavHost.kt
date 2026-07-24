package com.ericsonmontero.tasklite.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ericsonmontero.tasklite.presentation.screens.addtask.AddTaskScreen
import com.ericsonmontero.tasklite.presentation.screens.task.TaskScreen

@Composable
fun TaskNavHost(
    modifier: Modifier = Modifier ,
    navController: NavHostController,
    startDestination: Any = TaskNavRoute.TaskScreen

) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<TaskNavRoute.TaskScreen> {
            TaskScreen(
                onBackPressed = { navController.popBackStack() },
                goToEditTask = {
                    navController.navigate(TaskNavRoute.EditTaskScreen(it))
                }
            )
        }
        composable<TaskNavRoute.EditTaskScreen> {
            AddTaskScreen(
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}