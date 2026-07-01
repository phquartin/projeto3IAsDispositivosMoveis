package com.example.tarefas.data.repository

import com.example.tarefas.data.model.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositório de CRUD de tarefas no Cloud Firestore.
 *
 * As tarefas ficam organizadas por usuário na subcoleção:
 *   users/{userId}/tasks/{taskId}
 *
 * A leitura é feita em tempo real via [observeTasks] usando addSnapshotListener.
 */
@Singleton
class TaskRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private fun tasksCollection(userId: String) =
        firestore.collection("users").document(userId).collection("tasks")

    /**
     * Observa as tarefas do usuário em tempo real. Cada alteração no Firestore
     * dispara uma nova emissão da lista.
     */
    fun observeTasks(userId: String): Flow<List<Task>> = callbackFlow {
        val registration = tasksCollection(userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val tasks = snapshot?.documents
                    ?.mapNotNull { it.toObject(Task::class.java) }
                    .orEmpty()
                trySend(tasks)
            }
        awaitClose { registration.remove() }
    }

    /** Cria uma nova tarefa. */
    suspend fun addTask(
        userId: String,
        title: String,
        description: String,
        priority: String
    ): Result<Unit> = runCatching {
        val data = hashMapOf(
            "title" to title.trim(),
            "description" to description.trim(),
            "completed" to false,
            "priority" to priority,
            "createdAt" to Timestamp.now()
        )
        tasksCollection(userId).add(data).await()
        Unit
    }

    /** Atualiza uma tarefa existente. */
    suspend fun updateTask(
        userId: String,
        taskId: String,
        title: String,
        description: String,
        priority: String
    ): Result<Unit> = runCatching {
        val data = mapOf(
            "title" to title.trim(),
            "description" to description.trim(),
            "priority" to priority
        )
        tasksCollection(userId).document(taskId).update(data).await()
    }

    /** Marca/desmarca uma tarefa como concluída. */
    suspend fun setCompleted(
        userId: String,
        taskId: String,
        completed: Boolean
    ): Result<Unit> = runCatching {
        tasksCollection(userId).document(taskId).update("completed", completed).await()
    }

    /** Remove uma tarefa. */
    suspend fun deleteTask(userId: String, taskId: String): Result<Unit> = runCatching {
        tasksCollection(userId).document(taskId).delete().await()
    }
}
