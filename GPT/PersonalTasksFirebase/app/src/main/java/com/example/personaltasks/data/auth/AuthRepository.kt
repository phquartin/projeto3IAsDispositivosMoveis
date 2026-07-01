package com.example.personaltasks.data.auth

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: FirebaseUser?
    val currentUserFlow: Flow<FirebaseUser?>

    suspend fun login(email: String, password: String)
    suspend fun register(email: String, password: String)
    suspend fun sendPasswordReset(email: String)
    fun logout()
}
