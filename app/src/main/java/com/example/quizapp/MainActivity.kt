package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.data.AppDatabase
import com.example.quizapp.data.SampleData
import com.example.quizapp.ui.navigation.* // Importa suas rotas (signIn, signUp, etc)
import com.example.quizapp.ui.screens.QuizScreen
import com.example.quizapp.ui.theme.QuizAppTheme
import com.example.quizapp.ui.viewmodel.UserViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getDatabase(this)
        val userDao = database.userDao()
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                val navController = rememberNavController()
                val userViewModel: UserViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return UserViewModel(userDao = userDao) as T
                        }
                    }
                )

                QuizNavHost(navController = navController, userViewModel = userViewModel)
            }
        }
    }
}

@Composable
fun QuizNavHost(navController: NavHostController, userViewModel: UserViewModel) {


    NavHost(
        navController = navController,
        startDestination = signInRoute
    ) {
        // Configuração da tela de Login
        signInScreen(
            onNavigateToHome = {
                userViewModel.fetchUserData()
                navController.navigate(homeRoute) {
                    popUpTo(signInRoute) { inclusive = true }
                }
            },
            onNavigateToSignUp = {
                navController.navigateToSignUp()
            }
        )

        // Configuração da tela de Cadastro
        signUpScreen(
            onNavigationToSignIn = {
                navController.popBackStack()
            }
        )

        // Configuração da Home
        homeScreen(
            onNavigateToRanking = {
                userViewModel.fetchUserData()
                navController.navigate(rankingRoute)
            },
            onNavigateToProfile = {
                userViewModel.fetchUserData()
                navController.navigate(statsRoute)
            },
            onNavigateToQuiz = { quizId ->
                navController.navigate("quiz_screen/$quizId")
            },
            viewModel = userViewModel
        )

        // Configuração do Ranking
        rankingScreen(
            onNavigateToHome = {
                userViewModel.fetchUserData()
                navController.navigate(homeRoute)
            },
            onNavigateToProfile = {
                userViewModel.fetchUserData()
                navController.navigate(statsRoute) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        // Perfil / Estatísticas (Onde incluímos o botão vermelho de Sair)
        profileScreen(
            onLogout = {
                navController.navigate(signInRoute) {
                    popUpTo(0)
                    userViewModel.fetchUserData()
                }
            },
            onHomeClick = {
                userViewModel.fetchUserData()
                navController.navigate(homeRoute)
            },
            onRankingClick = {
                userViewModel.fetchUserData()
                navController.navigate(rankingRoute)
            },
            viewModel = userViewModel
        )


        // Rota que aceita um argumento (o nome do quiz)
        composable("quiz_screen/{quizId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("quizId") ?: ""
            QuizScreen(
                quizId = id,
                onQuizFinished = { score, timeFormatted ->
                    val quiz = SampleData.quizCategories.find { it.id.toString() == id }
                    userViewModel.addQuizResult(
                        quizTitle = quiz?.title ?: "Quiz",
                        score = score,
                        totalQuestions = quiz?.questionCount ?: 0,
                        time = timeFormatted

                    )
                    navController.navigate(statsRoute){
                        popUpTo("quiz_screen/$id") { inclusive = true }
                    }
                }
            )
        }
    }
}