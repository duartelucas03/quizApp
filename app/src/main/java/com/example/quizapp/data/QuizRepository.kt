package com.example.quizapp.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await // Import vital para resolver o erro do await()
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
class QuizRepository(
    private val questionDao: QuestionDao,
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun syncQuestions() {
        try {
            val snapshot = firestore.collection("questions").get().await()
            val questions = snapshot.documents.map { doc ->
                Question(
                    id = doc.id,
                    text = doc.getString("text") ?: "",
                    optionA = doc.getString("a") ?: "",
                    optionB = doc.getString("b") ?: "",
                    optionC = doc.getString("c") ?: "",
                    optionD = doc.getString("d") ?: "",
                    answer = doc.getLong("answer")?.toInt() ?: 0
                )
            }
            questionDao.insertAll(questions)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveRanking(username: String, score: Int) {
        // Usamos o nome como ID. Na primeira vez, o Firebase cria o documento "teste".
        // Na segunda vez, ele encontra o documento "teste" e soma o valor.
        val userRef = firestore.collection("ranking").document(username)

        val data = hashMapOf(
            "user" to username,
            "score" to FieldValue.increment(score.toLong()), // Soma desde a primeira vez
            "timestamp" to System.currentTimeMillis()
        )

        // O 'merge' é o que garante que a primeira jogada funcione igual às outras
        userRef.set(data, SetOptions.merge())
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}