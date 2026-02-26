package com.example.quizapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.FirebaseAuthRepository
import com.example.quizapp.ui.state.SignInUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val repository: FirebaseAuthRepository = FirebaseAuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()
    private val _signInIsSuccessful = MutableStateFlow(false)
    val signInIsSuccessful: StateFlow<Boolean> = _signInIsSuccessful.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onUserChange = { user ->
                    _uiState.update { it.copy(user = user) }
                },
                onPasswordChange = { pwd ->
                    _uiState.update { it.copy(password = pwd) }
                },
                onTogglePasswordVisibility = {
                    _uiState.update { it.copy(isShowPassword = !it.isShowPassword) }
                }
            )
        }
    }

    fun signIn() {
        val current = _uiState.value

        if (current.user.isBlank() || current.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Preencha usuário e senha") }
            return
        }

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(errorMessage = null) }

                repository.signIn(current.user, current.password)

                _signInIsSuccessful.value = true
            } catch (e: Exception) {

                _uiState.update { it.copy(errorMessage = "Erro ao entrar: ${e.localizedMessage}") }
            }
        }
    }
}