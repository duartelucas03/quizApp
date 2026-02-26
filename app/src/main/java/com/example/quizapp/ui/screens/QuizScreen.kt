package com.example.quizapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.quizapp.data.QuestionDao
import com.example.quizapp.data.SampleData

@Composable
fun QuizScreen(
    quizId: String,
    questionDao: QuestionDao,
    onQuizFinished: (Int, String) -> Unit
) {
    val startTime = remember { System.currentTimeMillis() }
    val quizData = remember(quizId) {
        SampleData.quizCategories.find { it.id.toString() == quizId }
    }

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }

    val currentQuestion = quizData?.questions?.getOrNull(currentQuestionIndex)
    val totalQuestions = quizData?.questions?.size ?: 0
    val progress = if (totalQuestions > 0) (currentQuestionIndex + 1).toFloat() / totalQuestions else 0f

    // --- SOLUÇÃO PARA O ERRO: Transformamos os campos individuais em uma lista ---
    val options = remember(currentQuestion) {
        if (currentQuestion != null) {
            listOf(
                currentQuestion.optionA,
                currentQuestion.optionB,
                currentQuestion.optionC,
                currentQuestion.optionD
            )
        } else emptyList()
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp).safeDrawingPadding()) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.secondaryContainer
                )
                Text(
                    text = "Questão ${currentQuestionIndex + 1} de $totalQuestions",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentQuestion?.text ?: "Fim das perguntas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Usamos a nossa nova lista 'options' aqui
            options.forEachIndexed { index, option ->
                Button(
                    onClick = {
                        // Comparamos com 'answer' (o novo nome do campo)
                        if (index == currentQuestion?.answer) {
                            score += 10
                        }

                        if (currentQuestionIndex < totalQuestions - 1) {
                            currentQuestionIndex++
                        } else {
                            val endTime = System.currentTimeMillis()
                            val seconds = (endTime - startTime) / 1000
                            val minutes = seconds / 60
                            val formattedTime = "%02d:%02d".format(minutes, seconds % 60)
                            onQuizFinished(score, formattedTime)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = option)
                }
            }
        }
    }
}