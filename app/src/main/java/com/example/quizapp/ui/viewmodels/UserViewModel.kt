package com.example.quizapp.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.FirebaseAuthRepository
import com.example.quizapp.data.QuizHistoryEntity
import com.example.quizapp.data.UserDao
import com.example.quizapp.data.UserProfileEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.quizapp.ui.screens.HistoryEntry
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf

class UserViewModel(
    private val repository: FirebaseAuthRepository = FirebaseAuthRepository(),
    private val userDao: UserDao
) : ViewModel() {

    // 1. O fluxo principal que controla quem está logado
    private val userFlow = MutableStateFlow(repository.getCurrentUser())

    // 2. Nome do usuário (Derivado do Perfil do Room)
    @OptIn(ExperimentalCoroutinesApi::class)
    val userName: StateFlow<String> = userFlow.flatMapLatest { user ->
        if (user != null) {
            userDao.getProfile(user.uid).map { it?.name ?: user.displayName ?: "Usuário" }
        } else flowOf("Usuário")
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Usuário")

    // 3. Fluxo 1: Dados acumulados do Perfil (Conectado ao Room)
    @OptIn(ExperimentalCoroutinesApi::class)
    val userProfile: StateFlow<UserProfileEntity?> = userFlow.flatMapLatest { user ->
        if (user != null) userDao.getProfile(user.uid)
        else flowOf(null)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // 4. Fluxo 2: Lista de Histórico Individual (Conectado ao Room)
    @OptIn(ExperimentalCoroutinesApi::class)
    val quizHistory: StateFlow<List<QuizHistoryEntity>> = userFlow.flatMapLatest { user ->
        if (user != null) userDao.getHistoryByUser(user.uid)
        else flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        fetchUserData()
    }

    fun fetchUserData() {
        val firebaseUser = repository.getCurrentUser()
        userFlow.value = firebaseUser

        firebaseUser?.let { user ->
            viewModelScope.launch {
                user.reload().addOnSuccessListener {
                    val remoteName = user.displayName ?: "Usuário"

                    viewModelScope.launch {
                        // 1. Sincroniza o Perfil
                        val current = userDao.getProfile(user.uid).firstOrNull()
                        if (current == null || current.name != remoteName) {
                            userDao.insertProfile(
                                UserProfileEntity(
                                    uid = user.uid,
                                    name = remoteName,
                                    email = user.email ?: "",
                                    totalQuizzes = current?.totalQuizzes ?: 0,
                                    totalPoints = current?.totalPoints ?: 0,
                                    totalQuestions = current?.totalQuestions ?: 0
                                )
                            )
                        }

                        val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()
                        db.collection("users").document(user.uid).collection("history")
                            .get()
                            .addOnSuccessListener { snapshot ->
                                if (!snapshot.isEmpty) {
                                    viewModelScope.launch {
                                        val cloudHistory = snapshot.documents.map { doc ->
                                            QuizHistoryEntity(
                                                userId = user.uid,
                                                quizTitle = doc.getString("quizTitle") ?: "",
                                                score = doc.getString("score") ?: "",
                                                timeSpent = doc.getString("timeSpent") ?: "",
                                                timestamp = doc.getLong("timestamp") ?: 0L
                                            )
                                        }

                                        userDao.clearHistoryByUser(user.uid)
                                        userDao.insertAllHistory(cloudHistory)
                                    }
                                }
                            }
                            .addOnFailureListener {
                                it.printStackTrace()
                            }
                    }
                }
            }
        }
    }

    /** GEMINI - início
     * Prompt: Implmentei os resultados para serem salvos localmente no room. Como fazer o mesmo
     * para ser salvo no firebase database?
     */


    fun addQuizResult(quizTitle: String, score: Int, totalQuestions: Int, time: String) {
        val uid = repository.getCurrentUser()?.uid ?: return

        viewModelScope.launch {

            val newEntry = QuizHistoryEntity(
                userId = uid,
                quizTitle = quizTitle,
                score = "$score/${totalQuestions * 10}",
                timeSpent = time,
            )


            userDao.insertHistory(newEntry)

            val currentProfile = userDao.getProfile(uid).firstOrNull()
                ?: UserProfileEntity(uid, "Usuário", "")

            val updatedProfile = currentProfile.copy(
                totalQuizzes = currentProfile.totalQuizzes + 1,
                totalPoints = currentProfile.totalPoints + score,
                totalQuestions = currentProfile.totalQuestions + totalQuestions
            )
            userDao.insertProfile(updatedProfile)


            val userCloudData = hashMapOf(
                "name" to updatedProfile.name,
                "totalQuizzes" to updatedProfile.totalQuizzes,
                "totalPoints" to updatedProfile.totalPoints,
                "totalQuestions" to updatedProfile.totalQuestions,
                "lastUpdate" to System.currentTimeMillis()
            )
            repository.saveToFirestore("users/$uid", userCloudData)

            val historyCloudData = hashMapOf(
                "quizTitle" to newEntry.quizTitle,
                "score" to newEntry.score,
                "timeSpent" to newEntry.timeSpent,
                "timestamp" to newEntry.timestamp
            )
            repository.saveToFirestore("users/$uid/history", historyCloudData)
        }
    }
    /** GEMINI - final */
}