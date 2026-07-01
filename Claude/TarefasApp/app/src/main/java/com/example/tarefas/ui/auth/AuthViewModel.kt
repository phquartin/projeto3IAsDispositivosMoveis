package com.example.tarefas.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarefas.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Estado da UI das telas de autenticação. */
data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val infoMessage: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    /** true se há usuário logado. Usado pela navegação para decidir a tela inicial. */
    val isLoggedIn: StateFlow<Boolean> = authRepository.authState
        .map { it != null }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = authRepository.currentUser != null
        )

    fun login(email: String, password: String) {
        if (!validate(email, password)) return
        _uiState.value = AuthUiState(isLoading = true)
        viewModelScope.launch {
            authRepository.login(email, password)
                .onSuccess { _uiState.value = AuthUiState() }
                .onFailure { e ->
                    _uiState.value = AuthUiState(errorMessage = mapError(e))
                }
        }
    }

    fun register(email: String, password: String, confirmPassword: String) {
        if (!validate(email, password)) return
        if (password != confirmPassword) {
            _uiState.value = AuthUiState(errorMessage = "As senhas não coincidem.")
            return
        }
        _uiState.value = AuthUiState(isLoading = true)
        viewModelScope.launch {
            authRepository.register(email, password)
                .onSuccess { _uiState.value = AuthUiState() }
                .onFailure { e ->
                    _uiState.value = AuthUiState(errorMessage = mapError(e))
                }
        }
    }

    fun sendPasswordReset(email: String) {
        if (email.isBlank()) {
            _uiState.value = AuthUiState(errorMessage = "Informe seu e-mail.")
            return
        }
        _uiState.value = AuthUiState(isLoading = true)
        viewModelScope.launch {
            authRepository.sendPasswordReset(email)
                .onSuccess {
                    _uiState.value = AuthUiState(
                        infoMessage = "E-mail de recuperação enviado. Verifique sua caixa de entrada."
                    )
                }
                .onFailure { e ->
                    _uiState.value = AuthUiState(errorMessage = mapError(e))
                }
        }
    }

    fun logout() = authRepository.logout()

    fun consumeMessages() {
        _uiState.value = _uiState.value.copy(errorMessage = null, infoMessage = null)
    }

    private fun validate(email: String, password: String): Boolean {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState(errorMessage = "Preencha e-mail e senha.")
            return false
        }
        if (password.length < 6) {
            _uiState.value = AuthUiState(errorMessage = "A senha deve ter ao menos 6 caracteres.")
            return false
        }
        return true
    }

    private fun mapError(e: Throwable): String =
        e.localizedMessage ?: "Ocorreu um erro. Tente novamente."
}
