package com.example.quizapp.ui.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.quizapp.ui.screens.ProfileScreen
import com.example.quizapp.ui.viewmodel.UserViewModel

const val statsRoute = "stats"
fun NavGraphBuilder.profileScreen(
    viewModel: UserViewModel,
    onLogout: () -> Unit,
    onHomeClick: () -> Unit,
    onRankingClick: () -> Unit
) {
    composable(statsRoute) {
        ProfileScreen(
            viewModel = viewModel,
            onHomeClick = onHomeClick,
            onRankingClick = onRankingClick,
            onLogoutClick = onLogout,

        )
    }
}