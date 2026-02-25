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

    // Estado para a navegação saber que o login funcionou
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

        // Validação simples antes de chamar o Firebase
        if (current.user.isBlank() || current.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Preencha usuário e senha") }
            return
        }

        viewModelScope.launch {
            try {
                // Limpa erro anterior
                _uiState.update { it.copy(errorMessage = null) }

                // No Firebase, o "user" aqui deve ser o e-mail
                repository.signIn(current.user, current.password)

                // Notifica sucesso
                _signInIsSuccessful.value = true
            } catch (e: Exception) {
                // Atualiza o erro para aparecer na tela
                _uiState.update { it.copy(errorMessage = "Erro ao entrar: ${e.localizedMessage}") }
            }
        }
    }
}