package com.example.personaltasks.ui.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.personaltasks.data.task.TaskItem
import com.example.personaltasks.ui.theme.PersonalTasksTheme

@Composable
fun TasksScreen(
    userEmail: String,
    onLogout: () -> Unit,
    viewModel: TasksViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TasksContent(
        userEmail = userEmail,
        uiState = uiState,
        onLogout = onLogout,
        onAddTask = viewModel::addTask,
        onUpdateTask = viewModel::updateTask,
        onCompletedChange = viewModel::setTaskCompleted,
        onDeleteTask = viewModel::deleteTask,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TasksContent(
    userEmail: String,
    uiState: TasksUiState,
    onLogout: () -> Unit,
    onAddTask: (String, String) -> Unit,
    onUpdateTask: (TaskItem, String, String) -> Unit,
    onCompletedChange: (TaskItem, Boolean) -> Unit,
    onDeleteTask: (TaskItem) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var editingTask by remember { mutableStateOf<TaskItem?>(null) }
    var showTaskDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.errorMessage) {
        val message = uiState.errorMessage
        if (message != null) {
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Minhas tarefas")
                        if (userEmail.isNotBlank()) {
                            Text(
                                text = userEmail,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Sair",
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editingTask = null
                    showTaskDialog = true
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar tarefa",
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { padding ->
        when {
            uiState.isLoading -> LoadingState(padding)
            uiState.tasks.isEmpty() -> EmptyState(padding)
            else -> TasksList(
                padding = padding,
                tasks = uiState.tasks,
                onCompletedChange = onCompletedChange,
                onEditTask = { task ->
                    editingTask = task
                    showTaskDialog = true
                },
                onDeleteTask = onDeleteTask,
            )
        }
    }

    if (showTaskDialog) {
        TaskEditorDialog(
            task = editingTask,
            onDismiss = {
                showTaskDialog = false
                editingTask = null
            },
            onConfirm = { title, description ->
                val task = editingTask
                if (task == null) {
                    onAddTask(title, description)
                } else {
                    onUpdateTask(task, title, description)
                }
                showTaskDialog = false
                editingTask = null
            },
        )
    }
}

@Composable
private fun LoadingState(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyState(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Default.TaskAlt,
            contentDescription = null,
            modifier = Modifier.size(56.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "Nenhuma tarefa ainda",
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "Toque em adicionar para criar a primeira.",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun TasksList(
    padding: PaddingValues,
    tasks: List<TaskItem>,
    onCompletedChange: (TaskItem, Boolean) -> Unit,
    onEditTask: (TaskItem) -> Unit,
    onDeleteTask: (TaskItem) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = tasks,
            key = { it.id },
        ) { task ->
            TaskCard(
                task = task,
                onCompletedChange = { completed ->
                    onCompletedChange(task, completed)
                },
                onEdit = {
                    onEditTask(task)
                },
                onDelete = {
                    onDeleteTask(task)
                },
            )
        }
    }
}

@Composable
private fun TaskCard(
    task: TaskItem,
    onCompletedChange: (Boolean) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = task.completed,
                onCheckedChange = onCompletedChange,
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (task.completed) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                if (task.description.isNotBlank()) {
                    Text(
                        text = task.description,
                        modifier = Modifier.padding(top = 4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar tarefa",
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Excluir tarefa",
                )
            }
        }
    }
}

@Composable
private fun TaskEditorDialog(
    task: TaskItem?,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit,
) {
    var title by remember(task) { mutableStateOf(task?.title.orEmpty()) }
    var description by remember(task) { mutableStateOf(task?.description.orEmpty()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (task == null) "Nova tarefa" else "Editar tarefa")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Título") },
                    singleLine = true,
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    label = { Text("Descrição") },
                    minLines = 3,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(title, description) },
                enabled = title.isNotBlank(),
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun TasksContentPreview() {
    PersonalTasksTheme {
        TasksContent(
            userEmail = "usuario@email.com",
            uiState = TasksUiState(
                tasks = listOf(
                    TaskItem(
                        id = "1",
                        title = "Planejar semana",
                        description = "Separar tarefas pessoais e prioridades.",
                    ),
                    TaskItem(
                        id = "2",
                        title = "Comprar café",
                        completed = true,
                    ),
                ),
                isLoading = false,
            ),
            onLogout = {},
            onAddTask = { _, _ -> },
            onUpdateTask = { _, _, _ -> },
            onCompletedChange = { _, _ -> },
            onDeleteTask = {},
        )
    }
}
