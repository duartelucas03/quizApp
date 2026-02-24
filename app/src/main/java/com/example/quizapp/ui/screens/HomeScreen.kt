package com.example.quizapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.quizapp.data.SampleData
import com.example.quizapp.ui.theme.QuizAppTheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(
    onQuizClick: (String) -> Unit,
    onRankingClick: () -> Unit,
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
                    selected = true,
                    onClick = { },
                    icon = { Icon(painterResource(R.drawable.bottom_btn1), contentDescription = null, tint = Color.Black) },
                    label = { Text("Início", color = Color.Black) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onRankingClick,
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

            TopUserSection(userName = "Pedro")

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "Quizes disponíveis:",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 24.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )


            QuizList(onQuizClick = onQuizClick)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun TopUserSection(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "Profile",
            modifier = Modifier.size(55.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Olá, $userName",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f),
            color = Color.Black
        )
    }
}

@Composable
fun QuizList(onQuizClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        SampleData.quizCategories.forEach { quiz ->
            QuizRowCard(
                title = quiz.title,
                icon = quiz.icon,
                difficulty = quiz.difficulty,
                questions = quiz.questionCount,
                accentColor = quiz.color,
                onClick = { onQuizClick(quiz.id.toString()) }
            )
        }
    }
}

@Composable
fun QuizRowCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    difficulty: String,
    questions: Int,
    accentColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(45.dp)
                .background(accentColor.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = "$difficulty • $questions questões",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        
        Icon(
            painter = painterResource(id = R.drawable.right_arrow),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Color.LightGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    QuizAppTheme {
        HomeScreen(
            onQuizClick = {},
            onRankingClick = {},
            onProfileClick = {}
        )
    }
}