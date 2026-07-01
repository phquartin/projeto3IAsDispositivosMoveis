package com.example.personaltasks.ui.tasks;

import androidx.lifecycle.ViewModel;
import com.example.personaltasks.data.auth.AuthRepository;
import com.example.personaltasks.data.task.TaskItem;
import com.example.personaltasks.data.task.TasksRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fJ\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013J\b\u0010\u0014\u001a\u00020\u000fH\u0002J+\u0010\u0015\u001a\u00020\r2\u001c\u0010\u0016\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0017H\u0002\u00a2\u0006\u0002\u0010\u001aJ\u0016\u0010\u001b\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001dJ\u001e\u0010\u001e\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fJ\f\u0010\u001f\u001a\u00020\u000f*\u00020 H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006!"}, d2 = {"Lcom/example/personaltasks/ui/tasks/TasksViewModel;", "Landroidx/lifecycle/ViewModel;", "authRepository", "Lcom/example/personaltasks/data/auth/AuthRepository;", "tasksRepository", "Lcom/example/personaltasks/data/task/TasksRepository;", "(Lcom/example/personaltasks/data/auth/AuthRepository;Lcom/example/personaltasks/data/task/TasksRepository;)V", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/example/personaltasks/ui/tasks/TasksUiState;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addTask", "", "title", "", "description", "deleteTask", "task", "Lcom/example/personaltasks/data/task/TaskItem;", "requireUserId", "runTaskAction", "action", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/jvm/functions/Function1;)V", "setTaskCompleted", "completed", "", "updateTask", "readableMessage", "", "app_debug"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class TasksViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.personaltasks.data.auth.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.personaltasks.data.task.TasksRepository tasksRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.personaltasks.ui.tasks.TasksUiState> uiState = null;
    
    @javax.inject.Inject()
    public TasksViewModel(@org.jetbrains.annotations.NotNull()
    com.example.personaltasks.data.auth.AuthRepository authRepository, @org.jetbrains.annotations.NotNull()
    com.example.personaltasks.data.task.TasksRepository tasksRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.personaltasks.ui.tasks.TasksUiState> getUiState() {
        return null;
    }
    
    public final void addTask(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description) {
    }
    
    public final void updateTask(@org.jetbrains.annotations.NotNull()
    com.example.personaltasks.data.task.TaskItem task, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description) {
    }
    
    public final void setTaskCompleted(@org.jetbrains.annotations.NotNull()
    com.example.personaltasks.data.task.TaskItem task, boolean completed) {
    }
    
    public final void deleteTask(@org.jetbrains.annotations.NotNull()
    com.example.personaltasks.data.task.TaskItem task) {
    }
    
    private final void runTaskAction(kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> action) {
    }
    
    private final java.lang.String requireUserId() {
        return null;
    }
    
    private final java.lang.String readableMessage(java.lang.Throwable $this$readableMessage) {
        return null;
    }
}