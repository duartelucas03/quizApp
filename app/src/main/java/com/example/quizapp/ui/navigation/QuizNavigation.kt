package com.example.quizapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quizapp.ui.screens.QuizScreen

const val quizRoute = "quiz/{quizId}"

fun NavGraphBuilder.quizScreen(onFinished: (Int, String) -> Unit) {
    composable(
        route = quizRoute,
        arguments = listOf(navArgument("quizId") { type = NavType.StringType })
    ) { backStackEntry ->
        val quizId = backStackEntry.arguments?.getString("quizId") ?: ""

        // Agora o onFinished combina com o que a QuizScreen envia
        QuizScreen(
            quizId = quizId,
            onQuizFinished = onFinished
        )
    }
}