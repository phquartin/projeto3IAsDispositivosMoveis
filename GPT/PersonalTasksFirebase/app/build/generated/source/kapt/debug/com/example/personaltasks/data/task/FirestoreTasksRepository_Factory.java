package com.example.personaltasks.data.task;

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
public final class FirestoreTasksRepository_Factory implements Factory<FirestoreTasksRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public FirestoreTasksRepository_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public FirestoreTasksRepository get() {
    return newInstance(firestoreProvider.get());
  }

  public static FirestoreTasksRepository_Factory create(
      Provider<FirebaseFirestore> firestoreProvider) {
    return new FirestoreTasksRepository_Factory(firestoreProvider);
  }

  public static FirestoreTasksRepository newInstance(FirebaseFirestore firestore) {
    return new FirestoreTasksRepository(firestore);
  }
}
