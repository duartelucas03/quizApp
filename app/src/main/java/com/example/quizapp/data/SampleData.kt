package com.example.quizapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.Storage
import androidx.compose.ui.graphics.Color
import com.example.quizapp.models.QuizCategory
import com.example.quizapp.models.Question

object SampleData {
    val quizCategories = listOf(
        QuizCategory(
            id = 1,
            title = "SQL Básico",
            icon = Icons.Default.DataArray,
            questionCount = 2, // Atualizado para o número de perguntas reais no teste
            difficulty = "Iniciante",
            color = Color(0xFF4CAF50),
            questions = listOf(
                Question(
                    text = "Qual comando é usado para buscar dados de uma tabela?",
                    options = listOf("INSERT", "UPDATE", "SELECT", "DELETE"),
                    correctAnswerIndex = 2
                ),
                Question(
                    text = "Qual cláusula filtra os resultados de uma consulta?",
                    options = listOf("WHERE", "GROUP BY", "ORDER BY", "HAVING"),
                    correctAnswerIndex = 0
                )
            )
        ),
        QuizCategory(
            id = 2,
            title = "Python para Dados",
            icon = Icons.Default.Code,
            questionCount = 2,
            difficulty = "Intermediário",
            color = Color(0xFF2196F3),
            questions = listOf(
                Question(
                    text = "Qual biblioteca é a mais usada para manipulação de DataFrames?",
                    options = listOf("NumPy", "Pandas", "Matplotlib", "Scikit-learn"),
                    correctAnswerIndex = 1
                ),
                Question(
                    text = "Como se verifica os tipos de dados de uma coluna no Pandas?",
                    options = listOf(".type()", ".dtypes", ".info", ".format()"),
                    correctAnswerIndex = 1
                )
            )
        ),
        QuizCategory(
            id = 3,
            title = "Engenharia de Dados",
            icon = Icons.Default.Storage,
            questionCount = 12,
            difficulty = "Avançado",
            color = Color(0xFFFF9800),
            questions = emptyList()
        ),
        QuizCategory(
            id = 4,
            title = "Análise Qualitativa",
            icon = Icons.Default.Analytics,
            questionCount = 8,
            difficulty = "Iniciante",
            color = Color(0xFF9C27B0),
            questions = emptyList()
        )
    )
}