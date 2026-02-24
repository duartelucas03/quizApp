package com.example.quizapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.Storage
import androidx.compose.ui.graphics.Color
import com.example.quizapp.models.QuizCategory

object SampleData {
    val quizCategories = listOf(
        QuizCategory(
            id = 1,
            title = "SQL Básico",
            icon = Icons.Default.DataArray,
            questionCount = 10,
            difficulty = "Iniciante",
            color = Color(0xFF4CAF50)
        ),
        QuizCategory(
            id = 2,
            title = "Python para Dados",
            icon = Icons.Default.Code,
            questionCount = 15,
            difficulty = "Intermediário",
            color = Color(0xFF2196F3)
        ),
        QuizCategory(
            id = 3,
            title = "Engenharia de Dados",
            icon = Icons.Default.Storage,
            questionCount = 12,
            difficulty = "Avançado",
            color = Color(0xFFFF9800)
        ),
        QuizCategory(
            id = 4,
            title = "Análise Qualitativa",
            icon = Icons.Default.Analytics,
            questionCount = 8,
            difficulty = "Iniciante",
            color = Color(0xFF9C27B0)
        )
    )
}