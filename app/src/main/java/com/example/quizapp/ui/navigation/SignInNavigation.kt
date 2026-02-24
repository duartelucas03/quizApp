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
        // Instanciando o ViewModel sem Koin usando o provider padrão do Android
        val viewModel: SignInViewModel = viewModel()

        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(uiState.isAuthenticated) {
            if (uiState.isAuthenticated) {
                onNavigateToHome()
            }
        }

        SignInScreen(
            uiState = uiState,
            onSignInClick = {
                viewModel.authenticate()
            },
            onSignUpClick = onNavigateToSignUp
        )
    }
}

fun NavHostController.navigateToSignIn(
    navOptions: NavOptions? = null
) {
    navigate(signInRoute, navOptions)
}