package com.example.taskmaster.repository

import com.example.taskmaster.model.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getTasks(userId: String): Flow<List<Task>> = callbackFlow {
        val subscription = firestore.collection("tasks")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val tasks = snapshot.documents.mapNotNull { it.toObject(Task::class.java) }
                    trySend(tasks)
                }
            }
        awaitClose { subscription.remove() }
    }

    suspend fun getTask(taskId: String): Task? {
        return try {
            val snapshot = firestore.collection("tasks").document(taskId).get().await()
            snapshot.toObject(Task::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun addTask(task: Task): Result<Unit> {
        return try {
            val docRef = firestore.collection("tasks").document()
            val newTask = task.copy(id = docRef.id)
            docRef.set(newTask).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateTask(task: Task): Result<Unit> {
        return try {
            firestore.collection("tasks").document(task.id).set(task).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteTask(taskId: String): Result<Unit> {
        return try {
            firestore.collection("tasks").document(taskId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
