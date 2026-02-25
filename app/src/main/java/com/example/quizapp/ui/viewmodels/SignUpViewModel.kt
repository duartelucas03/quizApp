package com.example.quizapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.FirebaseAuthRepository // Importe seu repositório
import com.example.quizapp.ui.state.SignUpUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: FirebaseAuthRepository = FirebaseAuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val _signUpIsSuccessful = MutableStateFlow(false)
    val signUpIsSuccessful: StateFlow<Boolean> = _signUpIsSuccessful.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onNameChange = { newName ->
                    _uiState.update { it.copy(name = newName) }
                },
                onEmailChange = { newEmail ->
                    _uiState.update { it.copy(email = newEmail) }
                },
                onPasswordChange = { newPassword ->
                    _uiState.update { it.copy(password = newPassword) }
                },
                onConfirmPasswordChange = { newConfirmPassword ->
                    _uiState.update { it.copy(confirmPassword = newConfirmPassword) }
                }
            )
        }
    }

    fun signUp() {
        val current = _uiState.value

        // 1. Validação local básica
        if (current.email.isBlank() || current.password.isBlank()) {
            _uiState.update { it.copy(error = "Preencha todos os campos") }
            return
        }

        if (current.password != current.confirmPassword) {
            _uiState.update { it.copy(error = "As senhas não coincidem") }
            return
        }

        // 2. Chamada ao Firebase (Assíncrona)
        viewModelScope.launch {
            try {
                // Limpa erros anteriores antes de tentar
                _uiState.update { it.copy(error = null) }

                // Tenta criar o usuário no Firebase
                repository.signUp(current.email, current.password, current.name)

                // Se chegou aqui, deu certo
                _signUpIsSuccessful.value = true
            } catch (e: Exception) {
                // Se der erro (ex: e-mail inválido), atualiza o estado de erro para a UI mostrar
                _uiState.update { it.copy(error = e.localizedMessage ?: "Erro desconhecido") }
            }
        }
    }
}