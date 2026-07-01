package com.example.personaltasks.data.task

data class TaskItem(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
)
