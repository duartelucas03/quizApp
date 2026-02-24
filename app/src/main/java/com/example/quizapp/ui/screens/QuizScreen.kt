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
import com.example.quizapp.data.SampleData

@Composable
fun QuizScreen(
    quizId: String,
    onQuizFinished: (Int, String) -> Unit // Retorna a pontuação final
) {
    val startTime = remember { System.currentTimeMillis() }
    // Busca os dados do quiz específico baseado no ID
    val quizData = remember(quizId) {
        SampleData.quizCategories.find { it.id.toString() == quizId }
    }

    // Controle de estado para pergunta atual e pontuação
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }

    // Dados da pergunta atual
    val currentQuestion = quizData?.questions?.getOrNull(currentQuestionIndex)
    val totalQuestions = quizData?.questions?.size ?: 0
    val progress = if (totalQuestions > 0) (currentQuestionIndex + 1).toFloat() / totalQuestions else 0f


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
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Exibe o texto da pergunta real vindo do SampleData
            Text(
                text = currentQuestion?.text ?: "Fim das perguntas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botões de Alternativas dinâmicos baseados na pergunta atual
            currentQuestion?.options?.forEachIndexed { index, option ->
                Button(
                    onClick = {
                        // Verifica se a resposta está correta antes de avançar
                        if (index == currentQuestion.correctAnswerIndex) {
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(option)
                }
            }
        }
    }
}