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

@Composable
fun QuizScreen(
    quizId: String,
    questionDao: QuestionDao,
    onQuizFinished: (Int, String) -> Unit
) {
    val startTime = remember { System.currentTimeMillis() }


    val allQuestions by questionDao.getAll().collectAsState(initial = emptyList())


    val quizQuestions = remember(allQuestions, quizId) {
        val prefix = when(quizId) {
            "1" -> "sql"
            "2" -> "py"
            "3" -> "eng"
            "4" -> "qual"
            else -> ""
        }
        allQuestions.filter { it.id.startsWith(prefix) }
    }

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }


    val currentQuestion = quizQuestions.getOrNull(currentQuestionIndex)
    val totalQuestions = quizQuestions.size
    val progress = if (totalQuestions > 0) (currentQuestionIndex + 1).toFloat() / totalQuestions else 0f

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

    /** GEMINI - início
     * Prompt: Preciso implementar um cronômetro e uma barra de progresso ao longo do quiz.
     */

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
            if (quizQuestions.isEmpty()) {

                CircularProgressIndicator()
                Text("Carregando questões do banco local...", modifier = Modifier.padding(top = 16.dp))
            } else {
                Text(
                    text = currentQuestion?.text ?: "Fim das perguntas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(32.dp))

                options.forEachIndexed { index, option ->
                    Button(
                        onClick = {
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
        /** GEMINI - Final **/
    }
}