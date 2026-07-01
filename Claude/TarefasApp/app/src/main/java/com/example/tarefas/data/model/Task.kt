package com.example.tarefas.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

/**
 * Prioridade de uma tarefa. Armazenada como String no Firestore (via [name]).
 */
enum class Priority {
    LOW, MEDIUM, HIGH;

    companion object {
        fun fromString(value: String?): Priority =
            entries.firstOrNull { it.name == value } ?: MEDIUM
    }
}

/**
 * Representa uma tarefa pessoal.
 *
 * Todos os campos têm valores padrão porque o Firestore precisa de um construtor
 * sem argumentos para desserializar documentos automaticamente (toObject()).
 *
 * O documento é armazenado em: users/{userId}/tasks/{id}
 */
data class Task(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val priority: String = Priority.MEDIUM.name,
    val createdAt: Timestamp? = null
) {
    /** Conveniência para obter a prioridade como enum. */
    val priorityEnum: Priority
        get() = Priority.fromString(priority)
}
