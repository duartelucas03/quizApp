package com.example.quizapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quizapp.ui.screens.QuizScreen
import com.example.quizapp.data.QuestionDao

/** GEMINI - início
 * Prompt: Preciso que o botão de quiz redirecione para tela de quiz, direcionando para o quiz que o usuário selecionou.
 */

const val quizRoute = "quiz/{quizId}"

fun NavGraphBuilder.quizScreen(questionDao: QuestionDao, onFinished: (Int, String) -> Unit) {
    composable(
        route = quizRoute,
        arguments = listOf(navArgument("quizId") { type = NavType.StringType })
    ) { backStackEntry ->
        val quizId = backStackEntry.arguments?.getString("quizId") ?: ""


        QuizScreen(
            quizId = quizId,
            questionDao = questionDao,
            onQuizFinished = onFinished

        )
    }
}

/** GEMINI - Final **/