package com.example.tarefas

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class anotada com @HiltAndroidApp para inicializar o container de DI do Hilt.
 */
@HiltAndroidApp
class TarefasApplication : Application()
