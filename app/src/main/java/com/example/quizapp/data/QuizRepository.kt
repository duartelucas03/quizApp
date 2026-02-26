package com.example.quizapp.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.example.quizapp.data.Question
class QuizRepository(
    private val questionDao: QuestionDao,
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    /** GEMINI - início
     * Prompt: Implmentei os resultados para serem salvos localmente no room. Como fazer o mesmo
     * para ser salvo no firebase database?
     * Acrescente a sincronização para quando o usuário ficar online novamente
     */
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
        val userRef = firestore.collection("ranking").document(username)

        val data = hashMapOf(
            "user" to username,
            "score" to FieldValue.increment(score.toLong()),
            "timestamp" to System.currentTimeMillis()
        )
        userRef.set(data, SetOptions.merge())
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
    suspend fun seedDatabaseIfNeeded() {
        try {
            val snapshot = firestore.collection("questions").get().await()
            if (snapshot.isEmpty) {
                SampleData.quizCategories.forEach { category ->
                    category.questions.forEach { q ->
                        val questionMap = hashMapOf(
                            "text" to q.text,
                            "a" to q.optionA,
                            "b" to q.optionB,
                            "c" to q.optionC,
                            "d" to q.optionD,
                            "answer" to q.answer
                        )
                        firestore.collection("questions")
                            .document(q.id)
                            .set(questionMap)
                            .await()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

/** GEMINI - Final **/
