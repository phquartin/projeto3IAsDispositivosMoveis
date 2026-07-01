package com.example.personaltasks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.personaltasks.ui.auth.AuthViewModel
import com.example.personaltasks.ui.auth.LoginScreen
import com.example.personaltasks.ui.auth.RegisterScreen
import com.example.personaltasks.ui.auth.ResetPasswordScreen
import com.example.personaltasks.ui.tasks.TasksScreen

@Composable
fun PersonalTasksApp(
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val user by authViewModel.user.collectAsStateWithLifecycle()

    if (user == null) {
        AuthNavHost(authViewModel = authViewModel)
    } else {
        TasksScreen(
            userEmail = user?.email.orEmpty(),
            onLogout = authViewModel::logout,
        )
    }
}

@Composable
private fun AuthNavHost(
    authViewModel: AuthViewModel,
) {
    val navController = rememberNavController()
    val authUiState by authViewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN,
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                uiState = authUiState,
                onLogin = authViewModel::login,
                onCreateAccount = {
                    authViewModel.clearMessages()
                    navController.navigate(Routes.REGISTER)
                },
                onForgotPassword = {
                    authViewModel.clearMessages()
                    navController.navigate(Routes.RESET_PASSWORD)
                },
                onMessageShown = authViewModel::clearMessages,
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                uiState = authUiState,
                onRegister = authViewModel::register,
                onBackToLogin = {
                    authViewModel.clearMessages()
                    navController.popBackStack()
                },
                onMessageShown = authViewModel::clearMessages,
            )
        }

        composable(Routes.RESET_PASSWORD) {
            ResetPasswordScreen(
                uiState = authUiState,
                onSendReset = authViewModel::sendPasswordReset,
                onBackToLogin = {
                    authViewModel.clearMessages()
                    navController.popBackStack()
                },
                onMessageShown = authViewModel::clearMessages,
            )
        }
    }
}
