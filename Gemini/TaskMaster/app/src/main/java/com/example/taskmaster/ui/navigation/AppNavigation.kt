package com.example.taskmaster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskmaster.ui.auth.AuthViewModel
import com.example.taskmaster.ui.auth.LoginScreen
import com.example.taskmaster.ui.auth.RegisterScreen
import com.example.taskmaster.ui.tasks.TaskDetailScreen
import com.example.taskmaster.ui.tasks.TaskListScreen

object Destinations {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val TASK_LIST = "task_list"
    const val TASK_DETAIL = "task_detail/{taskId}"
    
    fun createRouteForTaskDetail(taskId: String?) = "task_detail/${taskId ?: "new"}"
}

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val currentUser by authViewModel.currentUser.collectAsState()

    val startDestination = if (currentUser != null) Destinations.TASK_LIST else Destinations.LOGIN

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Destinations.REGISTER)
                }
            )
        }
        
        composable(Destinations.REGISTER) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Destinations.TASK_LIST) {
            TaskListScreen(
                onNavigateToDetail = { taskId ->
                    navController.navigate(Destinations.createRouteForTaskDetail(taskId))
                },
                onLogout = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.TASK_LIST) { inclusive = true }
                    }
                }
            )
        }
        
        composable(
            route = Destinations.TASK_DETAIL,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskIdArg = backStackEntry.arguments?.getString("taskId")
            val taskId = if (taskIdArg == "new") null else taskIdArg
            
            TaskDetailScreen(
                taskId = taskId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
