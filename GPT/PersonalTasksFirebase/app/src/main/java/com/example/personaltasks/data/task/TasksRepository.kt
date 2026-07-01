package com.example.personaltasks.data.task

import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun observeTasks(userId: String): Flow<List<TaskItem>>
    suspend fun addTask(userId: String, title: String, description: String)
    suspend fun updateTask(userId: String, task: TaskItem)
    suspend fun setTaskCompleted(userId: String, taskId: String, completed: Boolean)
    suspend fun deleteTask(userId: String, taskId: String)
}
