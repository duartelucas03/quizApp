package com.example.quizapp.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel // Importação para o ViewModel padrão
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.quizapp.ui.screens.SignUpScreen
import com.example.quizapp.ui.viewmodels.SignUpViewModel
import kotlinx.coroutines.launch

const val signUpRoute = "signUp"

// ... imports ...

fun NavGraphBuilder.signUpScreen(
    onNavigationToSignIn: () -> Unit
){
    composable(signUpRoute){
        val viewModel: SignUpViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()
        val signUpIsSuccessful by viewModel.signUpIsSuccessful.collectAsState()

        LaunchedEffect(signUpIsSuccessful) {
            if(signUpIsSuccessful) {
                onNavigationToSignIn()
            }
        }

        SignUpScreen(
            uiState = uiState,
            onSignUpClick = {
                viewModel.signUp()
            }
        )

    }
}
fun NavHostController.navigateToSignUp() {
    navigate(signUpRoute)
}