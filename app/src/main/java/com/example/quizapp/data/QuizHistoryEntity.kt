package com.example.quizapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_history")
data class QuizHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val quizTitle: String,
    val score: String,
    val timeSpent: String,
    val timestamp: Long = System.currentTimeMillis()
)