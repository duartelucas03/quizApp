package com.example.quizapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R
import com.example.quizapp.ui.theme.QuizAppTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizapp.ui.viewmodel.UserViewModel

@Composable
fun ProfileScreen(
    viewModel: UserViewModel = viewModel(),
    onHomeClick: () -> Unit,
    onRankingClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val userName2 by viewModel.userName.collectAsState()

    val history by viewModel.history.collectAsState()
    val totalQuizzes by viewModel.totalQuizzes.collectAsState()
    val totalPoints by viewModel.totalPoints.collectAsState()
    val totalQuestions by viewModel.totalQuestions.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier.fillMaxWidth().height(70.dp)
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onHomeClick,
                    icon = {
                        Icon(
                            painterResource(R.drawable.bottom_btn1),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    },
                    label = { Text("Início", color = Color.Black) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onRankingClick,
                    icon = {
                        Icon(
                            painterResource(R.drawable.bottom_btn2),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    },
                    label = { Text("Ranking", color = Color.Black) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(
                            painterResource(R.drawable.bottom_btn4),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    },
                    label = { Text("Perfil", color = Color.Black) }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
                .verticalScroll(scrollState)
        ) {
            // Header com Nome e Resumo Rápido
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(24.dp)
            ) {
                // Botão de Logout posicionado no canto superior direito
                Button(
                    onClick = onLogoutClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    modifier = Modifier.align(Alignment.TopEnd).height(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = "Sair", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }



                Column {
                    Text(
                        "Estatísticas de",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Text(
                        text = userName2,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Card de Resumo de Performance
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White.copy(alpha = 0.15f))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        val mediaCalculada = if (totalQuestions > 0) (totalPoints.toFloat() / (totalQuestions * 10)) * 100 else 0f
                        val mediaFormatada = "%.2f".format(mediaCalculada)

                        StatItem("Quizes jogados", totalQuizzes.toString())
                        StatItem("Média", mediaFormatada + "%")
                        StatItem("Pontos", totalPoints.toString())
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Histórico",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 24.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Lista de Histórico (Um por linha)
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Mock de Histórico - No futuro virá do seu banco SQL
                if (history.isEmpty()) {
                    Text("Nenhum quiz realizado", modifier = Modifier.padding(16.dp), color = Color.Gray)
                } else {
                    history.forEach { entry ->
                        HistoryRow(entry)
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = label, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
    }
}

data class HistoryEntry(
    val title: String,
    val score: String,
    val timeSpent: String,
    val color: Color)

@Composable
fun HistoryRow(entry: HistoryEntry) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(entry.color.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.History, contentDescription = null, tint = entry.color, modifier = Modifier.size(20.dp))
        }

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = entry.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black)
            Text(text = entry.timeSpent, fontSize = 12.sp, color = Color.Gray)
        }

        Text(
            text = entry.score,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF1A237E),
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    QuizAppTheme {
        ProfileScreen(
            onHomeClick = {},
            onRankingClick = {},
            onLogoutClick = {}
        )
    }
}