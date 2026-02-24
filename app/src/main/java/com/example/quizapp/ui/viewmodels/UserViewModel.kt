package com.example.quizapp.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.quizapp.ui.screens.HistoryEntry

class UserViewModel : ViewModel() {

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