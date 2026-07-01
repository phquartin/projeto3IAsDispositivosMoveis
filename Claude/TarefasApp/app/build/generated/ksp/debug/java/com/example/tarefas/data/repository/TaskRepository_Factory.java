package com.example.tarefas.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class TaskRepository_Factory implements Factory<TaskRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public TaskRepository_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public TaskRepository get() {
    return newInstance(firestoreProvider.get());
  }

  public static TaskRepository_Factory create(Provider<FirebaseFirestore> firestoreProvider) {
    return new TaskRepository_Factory(firestoreProvider);
  }

  public static TaskRepository newInstance(FirebaseFirestore firestore) {
    return new TaskRepository(firestore);
  }
}
