package com.example.quizapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_profile WHERE uid = :uid LIMIT 1")
    fun getUserById(uid: String): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: UserProfileEntity)

    @Query("SELECT * FROM user_profile WHERE uid = :uid")
    fun getProfile(uid: String): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: QuizHistoryEntity)

    @Query("SELECT * FROM quiz_history WHERE userId = :userId ORDER BY timestamp DESC")
    fun getHistoryByUser(userId: String): Flow<List<QuizHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllHistory(history: List<QuizHistoryEntity>)

    @Query("DELETE FROM quiz_history WHERE userId = :userId")
    suspend fun clearHistoryByUser(userId: String)

}