package com.example.personaltasks.ui.tasks;

import com.example.personaltasks.data.auth.AuthRepository;
import com.example.personaltasks.data.task.TasksRepository;
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
public final class TasksViewModel_Factory implements Factory<TasksViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<TasksRepository> tasksRepositoryProvider;

  public TasksViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<TasksRepository> tasksRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.tasksRepositoryProvider = tasksRepositoryProvider;
  }

  @Override
  public TasksViewModel get() {
    return newInstance(authRepositoryProvider.get(), tasksRepositoryProvider.get());
  }

  public static TasksViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider,
      Provider<TasksRepository> tasksRepositoryProvider) {
    return new TasksViewModel_Factory(authRepositoryProvider, tasksRepositoryProvider);
  }

  public static TasksViewModel newInstance(AuthRepository authRepository,
      TasksRepository tasksRepository) {
    return new TasksViewModel(authRepository, tasksRepository);
  }
}
