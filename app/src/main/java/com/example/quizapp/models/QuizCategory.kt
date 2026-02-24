package com.example.quizapp.models

import androidx.compose.ui.graphics.vector.ImageVector

data class QuizCategory(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val questionCount: Int,
    val difficulty: String,
    val color: androidx.compose.ui.graphics.Color
)