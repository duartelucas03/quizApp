package com.example.quizapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Composable
fun RankingScreen(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier.fillMaxWidth().height(70.dp)
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onHomeClick,
                    icon = { Icon(painterResource(R.drawable.bottom_btn1), contentDescription = null, tint = Color.Black) },
                    label = { Text("Início", color = Color.Black) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(painterResource(R.drawable.bottom_btn2), contentDescription = null, tint = Color.Black) },
                    label = { Text("Ranking", color = Color.Black) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onProfileClick,
                    icon = { Icon(painterResource(R.drawable.bottom_btn4), contentDescription = null, tint = Color.Black) },
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(color = MaterialTheme.colorScheme.primary, RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(60.dp)
                    )
                    Text(
                        text = "Ranking Geral",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Mock de dados para o Ranking
                val leaderboard = listOf(
                    Triple(1, "Ana Silva", 2850),
                    Triple(2, "Pedro (Você)", 2400),
                    Triple(3, "João Data", 2100),
                    Triple(4, "Maria Dev", 1950),
                    Triple(5, "Lucas Eng", 1800)
                )

                leaderboard.forEach { (rank, name, score) ->
                    RankingRow(rank = rank, name = name, score = score)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun RankingRow(rank: Int, name: String, score: Int) {
    val isTopThree = rank <= 3
    val rankColor = when (rank) {
        1 -> Color(0xFFFFD700) // Ouro
        2 -> Color(0xFFC0C0C0) // Prata
        3 -> Color(0xFFCD7F32) // Bronze
        else -> Color.Transparent
    }

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
                .size(35.dp)
                .clip(CircleShape)
                .background(if (isTopThree) rankColor else Color(0xFFEEEEEE)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rank.toString(),
                fontWeight = FontWeight.Bold,
                color = if (isTopThree) Color.White else Color.Black
            )
        }

        Spacer(Modifier.width(16.dp))


        Text(
            text = name,
            fontWeight = if (name.contains("Você")) FontWeight.ExtraBold else FontWeight.Medium,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = Color.Black
        )


        Text(
            text = "$score pts",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A237E),
            fontSize = 15.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RankingScreenPreview() {
    QuizAppTheme {
        RankingScreen(
            onHomeClick = {},
            onProfileClick = {}
        )
    }
}