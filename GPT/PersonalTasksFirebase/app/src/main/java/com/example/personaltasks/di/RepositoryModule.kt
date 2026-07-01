package com.example.personaltasks.di

import com.example.personaltasks.data.auth.AuthRepository
import com.example.personaltasks.data.auth.FirebaseAuthRepository
import com.example.personaltasks.data.task.FirestoreTasksRepository
import com.example.personaltasks.data.task.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(repository: FirebaseAuthRepository): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTasksRepository(repository: FirestoreTasksRepository): TasksRepository
}
