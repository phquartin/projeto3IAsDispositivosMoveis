package com.example.taskmaster.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.model.Task
import com.example.taskmaster.repository.AuthRepository
import com.example.taskmaster.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val currentUserId: Flow<String?> = authRepository.currentUser.map { it?.uid }

    val tasks: StateFlow<List<Task>> = currentUserId.flatMapLatest { userId ->
        if (userId != null) {
            taskRepository.getTasks(userId)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _currentTask = MutableStateFlow<Task?>(null)
    val currentTask: StateFlow<Task?> = _currentTask.asStateFlow()

    fun loadTask(taskId: String) = viewModelScope.launch {
        _currentTask.value = taskRepository.getTask(taskId)
    }

    fun clearCurrentTask() {
        _currentTask.value = null
    }

    fun saveTask(title: String, description: String, isCompleted: Boolean) = viewModelScope.launch {
        val userId = authRepository.getCurrentUserSync()?.uid ?: return@launch
        val task = _currentTask.value?.copy(
            title = title,
            description = description,
            isCompleted = isCompleted
        ) ?: Task(
            userId = userId,
            title = title,
            description = description,
            isCompleted = isCompleted
        )
        
        if (task.id.isEmpty()) {
            taskRepository.addTask(task)
        } else {
            taskRepository.updateTask(task)
        }
    }

    fun toggleTaskCompletion(task: Task) = viewModelScope.launch {
        taskRepository.updateTask(task.copy(isCompleted = !task.isCompleted))
    }

    fun deleteTask(taskId: String) = viewModelScope.launch {
        taskRepository.deleteTask(taskId)
    }
}
