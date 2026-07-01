package com.example.tarefas.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositório responsável pela autenticação via Firebase Authentication
 * (e-mail/senha, cadastro e recuperação de senha).
 */
@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    /** Usuário atualmente logado (ou null). */
    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    /** UID do usuário logado (ou null). */
    val currentUserId: String?
        get() = firebaseAuth.currentUser?.uid

    /**
     * Emite o estado de autenticação em tempo real. Emite o usuário atual sempre
     * que houver login/logout, permitindo redirecionar a navegação automaticamente.
     */
    val authState: Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser)
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    /** Login com e-mail e senha. */
    suspend fun login(email: String, password: String): Result<FirebaseUser> = runCatching {
        val result = firebaseAuth.signInWithEmailAndPassword(email.trim(), password).await()
        result.user ?: error("Usuário não encontrado após o login.")
    }

    /** Cadastro de novo usuário com e-mail e senha. */
    suspend fun register(email: String, password: String): Result<FirebaseUser> = runCatching {
        val result = firebaseAuth.createUserWithEmailAndPassword(email.trim(), password).await()
        result.user ?: error("Não foi possível criar o usuário.")
    }

    /** Envia e-mail de recuperação de senha. */
    suspend fun sendPasswordReset(email: String): Result<Unit> = runCatching {
        firebaseAuth.sendPasswordResetEmail(email.trim()).await()
    }

    /** Encerra a sessão. */
    fun logout() {
        firebaseAuth.signOut()
    }
}
