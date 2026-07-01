package com.example.tarefas.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarefas.data.model.Priority
import com.example.tarefas.data.model.Task
import com.example.tarefas.data.repository.AuthRepository
import com.example.tarefas.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Estado da tela de listagem de tarefas. */
data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private val userId: String?
        get() = authRepository.currentUserId

    init {
        observeTasks()
    }

    private fun observeTasks() {
        val uid = userId
        if (uid == null) {
            _uiState.value = TaskUiState(isLoading = false, errorMessage = "Sessão expirada.")
            return
        }
        viewModelScope.launch {
            taskRepository.observeTasks(uid)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Erro ao carregar tarefas."
                    )
                }
                .collect { tasks ->
                    _uiState.value = TaskUiState(tasks = tasks, isLoading = false)
                }
        }
    }

    fun addTask(title: String, description: String, priority: Priority) {
        val uid = userId ?: return
        if (title.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "O título é obrigatório.")
            return
        }
        viewModelScope.launch {
            taskRepository.addTask(uid, title, description, priority.name)
                .onFailure { setError(it) }
        }
    }

    fun updateTask(taskId: String, title: String, description: String, priority: Priority) {
        val uid = userId ?: return
        if (title.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "O título é obrigatório.")
            return
        }
        viewModelScope.launch {
            taskRepository.updateTask(uid, taskId, title, description, priority.name)
                .onFailure { setError(it) }
        }
    }

    fun toggleCompleted(task: Task) {
        val uid = userId ?: return
        viewModelScope.launch {
            taskRepository.setCompleted(uid, task.id, !task.completed)
                .onFailure { setError(it) }
        }
    }

    fun deleteTask(task: Task) {
        val uid = userId ?: return
        viewModelScope.launch {
            taskRepository.deleteTask(uid, task.id)
                .onFailure { setError(it) }
        }
    }

    fun consumeError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    private fun setError(e: Throwable) {
        _uiState.value = _uiState.value.copy(
            errorMessage = e.localizedMessage ?: "Ocorreu um erro."
        )
    }
}
