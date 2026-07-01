package com.example.personaltasks.di;

import com.example.personaltasks.data.auth.AuthRepository;
import com.example.personaltasks.data.auth.FirebaseAuthRepository;
import com.example.personaltasks.data.task.FirestoreTasksRepository;
import com.example.personaltasks.data.task.TasksRepository;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\tH\'\u00a8\u0006\n"}, d2 = {"Lcom/example/personaltasks/di/RepositoryModule;", "", "()V", "bindAuthRepository", "Lcom/example/personaltasks/data/auth/AuthRepository;", "repository", "Lcom/example/personaltasks/data/auth/FirebaseAuthRepository;", "bindTasksRepository", "Lcom/example/personaltasks/data/task/TasksRepository;", "Lcom/example/personaltasks/data/task/FirestoreTasksRepository;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class RepositoryModule {
    
    public RepositoryModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.personaltasks.data.auth.AuthRepository bindAuthRepository(@org.jetbrains.annotations.NotNull()
    com.example.personaltasks.data.auth.FirebaseAuthRepository repository);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.personaltasks.data.task.TasksRepository bindTasksRepository(@org.jetbrains.annotations.NotNull()
    com.example.personaltasks.data.task.FirestoreTasksRepository repository);
}