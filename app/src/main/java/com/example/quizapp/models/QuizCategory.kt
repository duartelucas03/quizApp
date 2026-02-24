package com.example.quizapp.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

data class QuizCategory(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val questionCount: Int,
    val difficulty: String,
    val color: Color,
    val questions: List<Question> = emptyList()
)