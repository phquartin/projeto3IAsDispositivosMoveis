package com.example.personaltasks.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personaltasks.data.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    val user: StateFlow<FirebaseUser?> = authRepository.currentUserFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = authRepository.currentUser,
    )

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        launchAuthAction {
            validateEmailAndPassword(email, password)
            authRepository.login(email, password)
        }
    }

    fun register(email: String, password: String, confirmPassword: String) {
        launchAuthAction {
            validateEmailAndPassword(email, password)
            require(password == confirmPassword) {
                "As senhas precisam ser iguais."
            }
            authRepository.register(email, password)
        }
    }

    fun sendPasswordReset(email: String) {
        launchAuthAction(successMessage = "E-mail de recuperação enviado.") {
            require(email.isNotBlank()) {
                "Informe seu e-mail."
            }
            authRepository.sendPasswordReset(email)
        }
    }

    fun logout() {
        authRepository.logout()
    }

    fun clearMessages() {
        _uiState.update {
            it.copy(errorMessage = null, successMessage = null)
        }
    }

    private fun launchAuthAction(
        successMessage: String? = null,
        action: suspend () -> Unit,
    ) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            runCatching {
                action()
            }.onSuccess {
                _uiState.value = AuthUiState(successMessage = successMessage)
            }.onFailure { throwable ->
                _uiState.value = AuthUiState(errorMessage = throwable.readableMessage())
            }
        }
    }

    private fun validateEmailAndPassword(email: String, password: String) {
        require(email.isNotBlank()) {
            "Informe seu e-mail."
        }
        require(password.length >= MIN_PASSWORD_LENGTH) {
            "A senha deve ter pelo menos $MIN_PASSWORD_LENGTH caracteres."
        }
    }

    private fun Throwable.readableMessage(): String =
        localizedMessage?.takeIf { it.isNotBlank() } ?: "Não foi possível concluir a operação."

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }
}
