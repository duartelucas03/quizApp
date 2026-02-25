package com.example.quizapp.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel // Importação necessária para o viewModel()
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.quizapp.ui.screens.SignInScreen
import com.example.quizapp.ui.viewmodels.SignInViewModel

const val signInRoute: String = "signIn"

fun NavGraphBuilder.signInScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    composable(signInRoute) {
        val viewModel: SignInViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()

        // Alteração: Observar o estado de sucesso específico, igual ao SignUp
        val signInIsSuccessful by viewModel.signInIsSuccessful.collectAsState()

        LaunchedEffect(signInIsSuccessful) {
            if (signInIsSuccessful) {
                onNavigateToHome()
            }
        }

        SignInScreen(
            uiState = uiState,
            onSignInClick = {
                // Alteração: Chamar o novo método que criamos no passo anterior
                viewModel.signIn()
            },
            onSignUpClick = onNavigateToSignUp
        )
    }
}