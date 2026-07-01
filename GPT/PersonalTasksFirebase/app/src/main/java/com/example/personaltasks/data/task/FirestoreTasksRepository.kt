package com.example.personaltasks.data.task

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@Singleton
class FirestoreTasksRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
) : TasksRepository {
    override fun observeTasks(userId: String): Flow<List<TaskItem>> = callbackFlow {
        val registration = tasksCollection(userId)
            .orderBy(CREATED_AT, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val tasks = snapshot
                    ?.documents
                    ?.mapNotNull { document ->
                        val title = document.getString(TITLE).orEmpty()
                        if (title.isBlank()) {
                            null
                        } else {
                            TaskItem(
                                id = document.id,
                                title = title,
                                description = document.getString(DESCRIPTION).orEmpty(),
                                completed = document.getBoolean(COMPLETED) ?: false,
                                createdAt = document.getLong(CREATED_AT) ?: 0L,
                                updatedAt = document.getLong(UPDATED_AT) ?: 0L,
                            )
                        }
                    }
                    .orEmpty()

                trySend(tasks)
            }

        awaitClose {
            registration.remove()
        }
    }

    override suspend fun addTask(userId: String, title: String, description: String) {
        val now = System.currentTimeMillis()
        val document = tasksCollection(userId).document()
        val data = mapOf(
            TITLE to title.trim(),
            DESCRIPTION to description.trim(),
            COMPLETED to false,
            CREATED_AT to now,
            UPDATED_AT to now,
        )
        document.set(data).await()
    }

    override suspend fun updateTask(userId: String, task: TaskItem) {
        val data = mapOf(
            TITLE to task.title.trim(),
            DESCRIPTION to task.description.trim(),
            COMPLETED to task.completed,
            UPDATED_AT to System.currentTimeMillis(),
        )
        tasksCollection(userId)
            .document(task.id)
            .set(data, SetOptions.merge())
            .await()
    }

    override suspend fun setTaskCompleted(userId: String, taskId: String, completed: Boolean) {
        val data = mapOf(
            COMPLETED to completed,
            UPDATED_AT to System.currentTimeMillis(),
        )
        tasksCollection(userId)
            .document(taskId)
            .update(data)
            .await()
    }

    override suspend fun deleteTask(userId: String, taskId: String) {
        tasksCollection(userId)
            .document(taskId)
            .delete()
            .await()
    }

    private fun tasksCollection(userId: String) =
        firestore
            .collection(USERS)
            .document(userId)
            .collection(TASKS)

    private companion object {
        const val USERS = "users"
        const val TASKS = "tasks"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val COMPLETED = "completed"
        const val CREATED_AT = "createdAt"
        const val UPDATED_AT = "updatedAt"
    }
}
