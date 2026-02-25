package com.example.quizapp.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val uid: String,
    val name: String,
    val email: String,
    val totalQuizzes: Int = 0,
    val totalPoints: Int = 0,
    val totalQuestions: Int = 0,
)
