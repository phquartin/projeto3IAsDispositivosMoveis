package com.example.tarefas.ui.navigation

/** Rotas de navegação do app. */
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object ForgotPassword : Screen("forgot_password")
    data object TaskList : Screen("task_list")
}
