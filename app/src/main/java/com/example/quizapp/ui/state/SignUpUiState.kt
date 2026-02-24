package com.example.quizapp.ui.state

data class SignUpUiState(
    val name: String = "",
    val onNameChange: (String) -> Unit = {},
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onConfirmPasswordChange: (String) -> Unit = {},
    val error: String? = null,
)