package com.example.quizapp.data

import androidx.room.Entity

@Entity(
    tableName = "quiz_history",
    primaryKeys = ["userId", "timestamp"]
)
data class QuizHistoryEntity(
    val userId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val quizTitle: String,
    val score: String,
    val timeSpent: String

)