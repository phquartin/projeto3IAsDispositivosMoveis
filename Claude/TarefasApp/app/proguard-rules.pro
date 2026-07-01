# Keep Firestore model classes (used with reflection for (de)serialization)
-keepclassmembers class com.example.tarefas.data.model.** {
    *;
}
-keep class com.example.tarefas.data.model.** { *; }

# Firebase
-keepattributes Signature
-keepattributes *Annotation*
