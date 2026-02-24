package com.example.quizapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.quizapp.ui.screens.HomeScreen
import com.example.quizapp.ui.screens.RankingScreen

const val rankingRoute = "ranking"

fun NavGraphBuilder.rankingScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    composable(rankingRoute) {
        RankingScreen(
            onHomeClick = onNavigateToHome,
            onProfileClick = onNavigateToProfile
        )
    }
}