package com.example.quizapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.quizapp.ui.screens.HomeScreen

const val homeRoute = "home"

fun NavGraphBuilder.homeScreen(
    onNavigateToQuiz: (String) -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    composable(homeRoute) {
        HomeScreen(
            onQuizClick = onNavigateToQuiz,
            onRankingClick = onNavigateToRanking,
            onProfileClick = onNavigateToProfile
        )
    }
}