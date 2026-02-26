package com.example.quizapp.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.quizapp.data.Question
data class QuizCategory(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val questionCount: Int,
    val difficulty: String,
    val color: Color,
    val questions: List<Question> = emptyList()
)