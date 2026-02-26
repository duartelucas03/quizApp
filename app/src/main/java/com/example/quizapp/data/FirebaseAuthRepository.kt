package com.example.quizapp.data

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await




class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    suspend fun signUp(email: String, password: String, name: String) {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        authResult.user?.updateProfile(profileUpdates)?.await()
    }

    // Use "signIn" para bater com o código do ViewModel anterior
    suspend fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    fun getCurrentUser() = firebaseAuth.currentUser

    /** GEMINI - início
     * Prompt: Implmentei os resultados para serem salvos localmente no room. Como fazer o mesmo
     * para ser salvo no firebase database?
     */


    private val firestore = Firebase.firestore

    fun saveUserProgress(profile: UserProfileEntity, history: List<QuizHistoryEntity>) {
        val uid = profile.uid

        firestore.collection("users").document(uid).set(profile)

        val historyRef = firestore.collection("users").document(uid).collection("history")
        history.forEach { entry ->
            historyRef.document(entry.id.toString()).set(entry)
        }
    }

    private val db = FirebaseFirestore.getInstance()

    fun saveToFirestore(path: String, data: Any) {
        if (path.contains("/history")) {

            db.collection(path).add(data)
        } else {

            db.document(path).set(data, SetOptions.merge())
        }
    }

    /** GEMINI - final */

    suspend fun saveSingleQuizResult(history: QuizHistoryEntity, userDao: UserDao) {
        val currentUser = firebaseAuth.currentUser ?: return
        val uid = currentUser.uid

        val historyToSave = history.copy(userId = uid)

        userDao.insertHistory(historyToSave)

        try {
            firestore.collection("users")
                .document(uid)
                .collection("history")
                .add(historyToSave)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
            // Opcional: Implementar lógica para tentar enviar novamente depois se estiver sem internet
        }
    }

    suspend fun syncUserHistory(userDao: UserDao) {
        val currentUser = firebaseAuth.currentUser ?: return
        val uid = currentUser.uid

        try {
            val snapshot = firestore.collection("users")
                .document(uid)
                .collection("history")
                .get()
                .await()

            val historyList = snapshot.documents.mapNotNull { doc ->
                doc.toObject(QuizHistoryEntity::class.java)
            }

            if (historyList.isNotEmpty()) {
                userDao.insertAllHistory(historyList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
