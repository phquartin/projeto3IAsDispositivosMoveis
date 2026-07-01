package com.example.tarefas.ui.tasks;

import com.example.tarefas.data.repository.AuthRepository;
import com.example.tarefas.data.repository.TaskRepository;
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
    "cast",
    "deprecation"
})
public final class TaskViewModel_Factory implements Factory<TaskViewModel> {
  private final Provider<TaskRepository> taskRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public TaskViewModel_Factory(Provider<TaskRepository> taskRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.taskRepositoryProvider = taskRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public TaskViewModel get() {
    return newInstance(taskRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static TaskViewModel_Factory create(Provider<TaskRepository> taskRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new TaskViewModel_Factory(taskRepositoryProvider, authRepositoryProvider);
  }

  public static TaskViewModel newInstance(TaskRepository taskRepository,
      AuthRepository authRepository) {
    return new TaskViewModel(taskRepository, authRepository);
  }
}
