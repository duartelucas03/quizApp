package com.example.quizapp.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.FirebaseAuthRepository
import com.example.quizapp.data.UserDao
import com.example.quizapp.data.UserProfileEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.quizapp.ui.screens.HistoryEntry
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class UserViewModel(
    // Injetando o repositório para acessar o usuário logado
    private val repository: FirebaseAuthRepository = FirebaseAuthRepository(),
    private val userDao: UserDao
) : ViewModel() {

    private val _userName = MutableStateFlow("Usuário")
    val userName: StateFlow<String> = _userName.asStateFlow()

    init {
        fetchUserData()
    }
    fun fetchUserData() {
        val firebaseUser = repository.getCurrentUser()
        firebaseUser?.let { user ->
            viewModelScope.launch {

                val localProfile = userDao.getUserById(user.uid).firstOrNull()
                localProfile?.let {
                    _userName.value = it.name
                }
                user.reload().addOnSuccessListener {
                    val remoteName = user.displayName ?: "Usuário"

                    if (_userName.value != remoteName) {
                        _userName.value = remoteName

                        viewModelScope.launch {
                            userDao.insertProfile(
                                UserProfileEntity(user.uid, remoteName, user.email ?: "")
                            )
                        }
                    }
                }
            }
        }
    }

    fun refreshUserData() {
        val user = repository.getCurrentUser()
        user?.reload()?.addOnSuccessListener {
            val updatedUser = repository.getCurrentUser()
            _userName.value = updatedUser?.displayName ?: "Visitante"
        }
    }
    private val _totalQuestions = MutableStateFlow(0)
    val totalQuestions: StateFlow<Int> = _totalQuestions.asStateFlow()
    private val _totalQuizzes = MutableStateFlow(0)
    val totalQuizzes: StateFlow<Int> = _totalQuizzes.asStateFlow()

    private val _totalPoints = MutableStateFlow(0)
    val totalPoints: StateFlow<Int> = _totalPoints.asStateFlow()

    private val _history = MutableStateFlow<List<HistoryEntry>>(emptyList())
    val history: StateFlow<List<HistoryEntry>> = _history.asStateFlow()

    fun addQuizResult(quizTitle: String, score: Int, totalQuestions: Int, time: String, color: Color) {
        _totalQuizzes.value += 1
        _totalPoints.value += score
        _totalQuestions.value += totalQuestions

        val maxPoints = totalQuestions * 10

        val newEntry = HistoryEntry(
            title = quizTitle,
            score = "$score/$maxPoints",
            timeSpent = time,
            color = color
        )
        _history.update { currentList -> listOf(newEntry) + currentList }
    }
}