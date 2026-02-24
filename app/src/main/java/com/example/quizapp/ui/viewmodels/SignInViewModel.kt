package com.example.quizapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.quizapp.ui.state.SignInUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onUserChange = { newUser ->
                    _uiState.update { it.copy(user = newUser, errorMessage = null) }
                },
                onPasswordChange = { newPassword ->
                    _uiState.update { it.copy(password = newPassword, errorMessage = null) }
                },
                onTogglePasswordVisibility = {
                    _uiState.update { it.copy(isShowPassword = !it.isShowPassword) }
                }
            )
        }
    }

    // Função que seu SignInNavigation.kt chama no onSignInClick
    fun authenticate() {
        if (validateLogin()) {
            val current = _uiState.value

            // Lógica de usuários fixos para teste
            val isAuthorized = (current.user == "admin" && current.password == "123456") ||
                    (current.user == "aluno@ufu.br" && current.password == "654321")

            if (isAuthorized) {
                _uiState.update { it.copy(isAuthenticated = true, errorMessage = null) }
            } else {
                _uiState.update { it.copy(isAuthenticated = false, errorMessage = "Usuário ou senha incorretos") }
            }
        }
    }

    private fun validateLogin(): Boolean {
        val current = _uiState.value
        return when {
            current.user.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "O usuário não pode estar vazio") }
                false
            }
            current.password.length < 6 -> {
                _uiState.update { it.copy(errorMessage = "A senha deve ter pelo menos 6 caracteres") }
                false
            }
            else -> true
        }
    }
}