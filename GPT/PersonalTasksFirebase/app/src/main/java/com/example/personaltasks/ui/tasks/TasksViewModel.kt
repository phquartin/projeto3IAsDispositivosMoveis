package com.example.personaltasks.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personaltasks.data.auth.AuthRepository
import com.example.personaltasks.data.task.TaskItem
import com.example.personaltasks.data.task.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TasksUiState(
    val tasks: List<TaskItem> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tasksRepository: TasksRepository,
) : ViewModel() {
    val uiState: StateFlow<TasksUiState> = authRepository.currentUserFlow
        .flatMapLatest { user ->
            if (user == null) {
                flowOf(TasksUiState(isLoading = false))
            } else {
                tasksRepository.observeTasks(user.uid)
                    .map { tasks ->
                        TasksUiState(tasks = tasks, isLoading = false)
                    }
                    .onStart {
                        emit(TasksUiState(isLoading = true))
                    }
                    .catch { throwable ->
                        emit(
                            TasksUiState(
                                isLoading = false,
                                errorMessage = throwable.readableMessage(),
                            ),
                        )
                    }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TasksUiState(),
        )

    fun addTask(title: String, description: String) {
        runTaskAction {
            require(title.isNotBlank()) {
                "Informe o título da tarefa."
            }
            val userId = requireUserId()
            tasksRepository.addTask(userId, title, description)
        }
    }

    fun updateTask(task: TaskItem, title: String, description: String) {
        runTaskAction {
            require(title.isNotBlank()) {
                "Informe o título da tarefa."
            }
            val userId = requireUserId()
            tasksRepository.updateTask(
                userId = userId,
                task = task.copy(
                    title = title,
                    description = description,
                ),
            )
        }
    }

    fun setTaskCompleted(task: TaskItem, completed: Boolean) {
        runTaskAction {
            val userId = requireUserId()
            tasksRepository.setTaskCompleted(userId, task.id, completed)
        }
    }

    fun deleteTask(task: TaskItem) {
        runTaskAction {
            val userId = requireUserId()
            tasksRepository.deleteTask(userId, task.id)
        }
    }

    private fun runTaskAction(action: suspend () -> Unit) {
        viewModelScope.launch {
            runCatching {
                action()
            }
        }
    }

    private fun requireUserId(): String =
        requireNotNull(authRepository.currentUser?.uid) {
            "Usuário não autenticado."
        }

    private fun Throwable.readableMessage(): String =
        localizedMessage?.takeIf { it.isNotBlank() } ?: "Não foi possível carregar suas tarefas."
}
