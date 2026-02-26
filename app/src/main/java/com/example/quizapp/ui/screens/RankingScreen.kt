package com.example.quizapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


data class RankingEntry(
    val name: String = "",
    val totalPoints: Int = 0
)

@Composable
fun RankingScreen(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var leaderboardReal by remember { mutableStateOf(listOf<RankingEntry>()) }
    var isLoading by remember { mutableStateOf(true) }

    val db = FirebaseFirestore.getInstance()


    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val currentUserName = firebaseUser?.displayName ?: firebaseUser?.email?.substringBefore("@") ?: "Usuario"


    LaunchedEffect(Unit) {
        db.collection("users")
            .orderBy("totalPoints", Query.Direction.DESCENDING)
            .limit(10)
            .addSnapshotListener { snapshot, error ->
                isLoading = false
                if (snapshot != null) {

                    leaderboardReal = snapshot.toObjects(RankingEntry::class.java)
                }
            }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier.fillMaxWidth().height(70.dp)
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onHomeClick,
                    icon = { Icon(painterResource(R.drawable.bottom_btn1), null, tint = Color.Gray) },
                    label = { Text("Início") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(painterResource(R.drawable.bottom_btn2), null, tint = MaterialTheme.colorScheme.primary) },
                    label = { Text("Ranking") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onProfileClick,
                    icon = { Icon(painterResource(R.drawable.bottom_btn4), null, tint = Color.Gray) },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "Ranking Geral",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (leaderboardReal.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhum ponto registrado.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    itemsIndexed(leaderboardReal) { index, entry ->

                        val isMe = entry.name == currentUserName || (entry.name == "teste" && currentUserName == "teste")
                        val displayName = if (isMe) "${entry.name} (Você)" else entry.name

                        RankingRow(rank = index + 1, name = displayName, score = entry.totalPoints, isCurrentUser = isMe)
                    }
                }
            }
        }
    }
}

@Composable
fun RankingRow(rank: Int, name: String, score: Int, isCurrentUser: Boolean) {
    val rankColor = when (rank) {
        1 -> Color(0xFFFFD700)
        2 -> Color(0xFFC0C0C0)
        3 -> Color(0xFFCD7F32)
        else -> Color(0xFFE0E0E0)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(rankColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rank.toString(),
                fontWeight = FontWeight.Bold,
                color = if (rank <= 3) Color.White else Color.DarkGray
            )
        }

        Spacer(Modifier.width(16.dp))

        Text(
            text = name,
            fontWeight = if (isCurrentUser) FontWeight.ExtraBold else FontWeight.Medium,
            modifier = Modifier.weight(1f),
            fontSize = 17.sp,
            color = if (isCurrentUser) MaterialTheme.colorScheme.primary else Color.Black
        )

        Text(
            text = "$score pts",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A237E),
            fontSize = 16.sp
        )
    }
}