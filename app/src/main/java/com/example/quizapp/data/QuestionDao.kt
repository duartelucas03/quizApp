package com.example.quizapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions")
    fun getAll(): Flow<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<Question>)
}