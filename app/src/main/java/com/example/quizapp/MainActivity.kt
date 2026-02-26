package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState // Importante para observar o perfil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.data.AppDatabase
import com.example.quizapp.data.QuizRepository
import com.example.quizapp.data.SampleData
import com.example.quizapp.ui.navigation.* import com.example.quizapp.ui.screens.QuizScreen
import com.example.quizapp.ui.theme.QuizAppTheme
import com.example.quizapp.ui.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth // Adicionado para o nome de fallback
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val userDao = database.userDao()
        val questionDao = database.questionDao()
        val quizRepository = QuizRepository(questionDao)
        val userViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(userDao = userDao) as T
            }
        })[UserViewModel::class.java]
        // Sincroniza questões para modo offline
        lifecycleScope.launch {
            quizRepository.seedDatabaseIfNeeded()
            quizRepository.syncQuestions()
            if (FirebaseAuth.getInstance().currentUser != null) {
                userViewModel.syncLocalHistoryToFirebase()
            }
        }

        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                val navController = rememberNavController()

                QuizNavHost(
                    navController = navController,
                    userViewModel = userViewModel,
                    quizRepository = quizRepository,
                    database = database
                )
            }
        }
    }
}

@Composable
fun QuizNavHost(
    navController: NavHostController,
    userViewModel: UserViewModel,
    quizRepository: QuizRepository,
    database: AppDatabase
) {
    // Observamos o perfil para garantir que ele esteja sempre atualizado na UI
    val userProfile = userViewModel.userProfile.collectAsState()

    NavHost(
        navController = navController,
        startDestination = signInRoute
    ) {
        signInScreen(
            onNavigateToHome = {
                userViewModel.fetchUserData()
                navController.navigate(homeRoute) {
                    popUpTo(signInRoute) { inclusive = true }
                }
            },
            onNavigateToSignUp = { navController.navigateToSignUp() }
        )

        signUpScreen(onNavigationToSignIn = { navController.popBackStack() })

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

        rankingScreen(
            onNavigateToHome = {
                userViewModel.fetchUserData()
                navController.navigate(homeRoute)
            },
            onNavigateToProfile = {
                userViewModel.fetchUserData()
                navController.navigate(statsRoute)
            }
        )

        profileScreen(
            onLogout = {
                navController.navigate(signInRoute) { popUpTo(0) }
            },
            onHomeClick = { navController.navigate(homeRoute) },
            onRankingClick = { navController.navigate(rankingRoute) },
            viewModel = userViewModel
        )

        composable("quiz_screen/{quizId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("quizId") ?: ""

            QuizScreen(
                quizId = id,
                questionDao = database.questionDao(),
                onQuizFinished = { score, timeFormatted ->
                    val quiz = SampleData.quizCategories.find { it.id.toString() == id }
                    val firebaseUser = FirebaseAuth.getInstance().currentUser
                    val userName = userProfile.value?.name ?: firebaseUser?.displayName ?: "Usuario"

                    userViewModel.addQuizResult(
                        quizTitle = quiz?.title ?: "Quiz",
                        score = score,
                        totalQuestions = quiz?.questionCount ?: 0,
                        time = timeFormatted
                    )

                    quizRepository.saveRanking(userName, score)

                    navController.navigate(statsRoute) {
                        popUpTo("quiz_screen/$id") { inclusive = true }
                    }
                }
            )
        }
    }}