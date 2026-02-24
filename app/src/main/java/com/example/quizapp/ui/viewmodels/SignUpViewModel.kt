package com.example.quizapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.quizapp.ui.state.SignUpUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    // Estado para controlar se o cadastro foi concluído com sucesso
    private val _signUpIsSuccessful = MutableStateFlow(false)
    val signUpIsSuccessful: StateFlow<Boolean> = _signUpIsSuccessful.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onEmailChange = { newUser ->
                    _uiState.update { it.copy(email = newUser) }
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

    // Função que o seu SignUpNavigation agora consegue chamar
    fun signUp() {
        val current = _uiState.value

        // Lógica simples de teste: se as senhas coincidirem e não estiverem vazias
        if (current.password.isNotEmpty() && current.password == current.confirmPassword) {
            _signUpIsSuccessful.value = true
        } else {
            _uiState.update { it.copy(error = "As senhas não coincidem ou são inválidas") }
        }
    }
}