package com.example.quizapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
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
}