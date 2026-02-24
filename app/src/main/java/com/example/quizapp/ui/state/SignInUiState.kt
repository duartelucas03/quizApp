package com.example.quizapp.ui.state

data class SignInUiState(
    val user: String = "",
    val password: String = "",
    val onUserChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val isShowPassword: Boolean = false,
    val onTogglePasswordVisibility: () -> Unit = {},
    val isAuthenticated: Boolean = false,
    val errorMessage: String? = null
)