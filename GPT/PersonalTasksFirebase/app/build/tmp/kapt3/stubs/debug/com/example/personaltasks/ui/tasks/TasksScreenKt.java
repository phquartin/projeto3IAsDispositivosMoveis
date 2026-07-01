package com.example.personaltasks.ui.tasks;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.tooling.preview.Preview;
import com.example.personaltasks.data.task.TaskItem;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000R\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a@\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0003\u001a:\u0010\u000e\u001a\u00020\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00010\u0011H\u0003\u001a\u008e\u0001\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0018\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00010\u00112\u001e\u0010\u0019\u001a\u001a\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00010\u001a2\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u00112\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\tH\u0003\u001a\b\u0010\u001c\u001a\u00020\u0001H\u0003\u001a`\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00070\u001f2\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u00112\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\t2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\tH\u0003\u001a(\u0010!\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00122\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010\"\u001a\u00020#H\u0007\u00a8\u0006$"}, d2 = {"EmptyState", "", "padding", "Landroidx/compose/foundation/layout/PaddingValues;", "LoadingState", "TaskCard", "task", "Lcom/example/personaltasks/data/task/TaskItem;", "onCompletedChange", "Lkotlin/Function1;", "", "onEdit", "Lkotlin/Function0;", "onDelete", "TaskEditorDialog", "onDismiss", "onConfirm", "Lkotlin/Function2;", "", "TasksContent", "userEmail", "uiState", "Lcom/example/personaltasks/ui/tasks/TasksUiState;", "onLogout", "onAddTask", "onUpdateTask", "Lkotlin/Function3;", "onDeleteTask", "TasksContentPreview", "TasksList", "tasks", "", "onEditTask", "TasksScreen", "viewModel", "Lcom/example/personaltasks/ui/tasks/TasksViewModel;", "app_debug"})
public final class TasksScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void TasksScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String userEmail, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout, @org.jetbrains.annotations.NotNull()
    com.example.personaltasks.ui.tasks.TasksViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void TasksContent(java.lang.String userEmail, com.example.personaltasks.ui.tasks.TasksUiState uiState, kotlin.jvm.functions.Function0<kotlin.Unit> onLogout, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onAddTask, kotlin.jvm.functions.Function3<? super com.example.personaltasks.data.task.TaskItem, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onUpdateTask, kotlin.jvm.functions.Function2<? super com.example.personaltasks.data.task.TaskItem, ? super java.lang.Boolean, kotlin.Unit> onCompletedChange, kotlin.jvm.functions.Function1<? super com.example.personaltasks.data.task.TaskItem, kotlin.Unit> onDeleteTask) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LoadingState(androidx.compose.foundation.layout.PaddingValues padding) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyState(androidx.compose.foundation.layout.PaddingValues padding) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TasksList(androidx.compose.foundation.layout.PaddingValues padding, java.util.List<com.example.personaltasks.data.task.TaskItem> tasks, kotlin.jvm.functions.Function2<? super com.example.personaltasks.data.task.TaskItem, ? super java.lang.Boolean, kotlin.Unit> onCompletedChange, kotlin.jvm.functions.Function1<? super com.example.personaltasks.data.task.TaskItem, kotlin.Unit> onEditTask, kotlin.jvm.functions.Function1<? super com.example.personaltasks.data.task.TaskItem, kotlin.Unit> onDeleteTask) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TaskCard(com.example.personaltasks.data.task.TaskItem task, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCompletedChange, kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TaskEditorDialog(com.example.personaltasks.data.task.TaskItem task, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onConfirm) {
    }
    
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    @androidx.compose.runtime.Composable()
    private static final void TasksContentPreview() {
    }
}