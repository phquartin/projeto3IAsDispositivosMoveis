package com.example.taskmaster.ui.tasks;

import com.example.taskmaster.repository.AuthRepository;
import com.example.taskmaster.repository.TaskRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class TasksViewModel_Factory implements Factory<TasksViewModel> {
  private final Provider<TaskRepository> taskRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public TasksViewModel_Factory(Provider<TaskRepository> taskRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.taskRepositoryProvider = taskRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public TasksViewModel get() {
    return newInstance(taskRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static TasksViewModel_Factory create(Provider<TaskRepository> taskRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new TasksViewModel_Factory(taskRepositoryProvider, authRepositoryProvider);
  }

  public static TasksViewModel newInstance(TaskRepository taskRepository,
      AuthRepository authRepository) {
    return new TasksViewModel(taskRepository, authRepository);
  }
}
