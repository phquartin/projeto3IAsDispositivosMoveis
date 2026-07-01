package com.example.personaltasks.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.personaltasks.ui.theme.PersonalTasksTheme
import androidx.compose.foundation.layout.ColumnScope

@Composable
fun LoginScreen(
    uiState: AuthUiState,
    onLogin: (String, String) -> Unit,
    onCreateAccount: () -> Unit,
    onForgotPassword: () -> Unit,
    onMessageShown: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AuthScaffold(
        title = "Entrar",
        uiState = uiState,
        onMessageShown = onMessageShown,
    ) { padding ->
        AuthContent(
            padding = padding,
            title = "Personal Tasks",
            subtitle = "Organize seu dia com sincronização em tempo real.",
        ) {
            EmailField(
                value = email,
                onValueChange = { email = it },
            )
            PasswordField(
                value = password,
                onValueChange = { password = it },
                label = "Senha",
            )
            Button(
                onClick = { onLogin(email, password) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
            ) {
                LoadingButtonContent(
                    isLoading = uiState.isLoading,
                    text = "Entrar",
                    icon = {
                        Icon(
                            imageVector = Icons.Default.TaskAlt,
                            contentDescription = null,
                        )
                    },
                )
            }
            TextButton(
                onClick = onForgotPassword,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text("Esqueci minha senha")
            }
            OutlinedButton(
                onClick = onCreateAccount,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Criar conta")
            }
        }
    }
}

@Composable
fun RegisterScreen(
    uiState: AuthUiState,
    onRegister: (String, String, String) -> Unit,
    onBackToLogin: () -> Unit,
    onMessageShown: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    AuthScaffold(
        title = "Cadastro",
        uiState = uiState,
        onMessageShown = onMessageShown,
        navigationIcon = {
            IconButton(onClick = onBackToLogin) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                )
            }
        },
    ) { padding ->
        AuthContent(
            padding = padding,
            title = "Criar conta",
            subtitle = "Use e-mail e senha para guardar suas tarefas no Firebase.",
        ) {
            EmailField(
                value = email,
                onValueChange = { email = it },
            )
            PasswordField(
                value = password,
                onValueChange = { password = it },
                label = "Senha",
            )
            PasswordField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirmar senha",
            )
            Button(
                onClick = { onRegister(email, password, confirmPassword) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
            ) {
                LoadingButtonContent(
                    isLoading = uiState.isLoading,
                    text = "Cadastrar",
                    icon = {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = null,
                        )
                    },
                )
            }
        }
    }
}

@Composable
fun ResetPasswordScreen(
    uiState: AuthUiState,
    onSendReset: (String) -> Unit,
    onBackToLogin: () -> Unit,
    onMessageShown: () -> Unit,
) {
    var email by remember { mutableStateOf("") }

    AuthScaffold(
        title = "Recuperar senha",
        uiState = uiState,
        onMessageShown = onMessageShown,
        navigationIcon = {
            IconButton(onClick = onBackToLogin) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                )
            }
        },
    ) { padding ->
        AuthContent(
            padding = padding,
            title = "Recuperar acesso",
            subtitle = "Receba um link de redefinição no seu e-mail.",
        ) {
            EmailField(
                value = email,
                onValueChange = { email = it },
            )
            Button(
                onClick = { onSendReset(email) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
            ) {
                LoadingButtonContent(
                    isLoading = uiState.isLoading,
                    text = "Enviar link",
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = null,
                        )
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AuthScaffold(
    title: String,
    uiState: AuthUiState,
    onMessageShown: () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val message = uiState.errorMessage ?: uiState.successMessage

    LaunchedEffect(message) {
        if (message != null) {
            snackbarHostState.showSnackbar(message)
            onMessageShown()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = navigationIcon,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        content = content,
    )
}

@Composable
private fun AuthContent(
    padding: PaddingValues,
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Default.TaskAlt,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = subtitle,
            modifier = Modifier.padding(top = 8.dp, bottom = 28.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        content()
    }
}

@Composable
private fun EmailField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        label = { Text("E-mail") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Mail,
                contentDescription = null,
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        ),
    )
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
            )
        },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
    )
}

@Composable
private fun LoadingButtonContent(
    isLoading: Boolean,
    text: String,
    icon: @Composable () -> Unit,
) {
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        icon()
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PersonalTasksTheme {
        LoginScreen(
            uiState = AuthUiState(),
            onLogin = { _, _ -> },
            onCreateAccount = {},
            onForgotPassword = {},
            onMessageShown = {},
        )
    }
}
