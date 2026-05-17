package com.example.practico3.viewmodel

import com.example.practico3.data.entities.Tarea
import com.example.practico3.data.entities.TareaConTags

sealed class TareaState {

    object Loading : TareaState()

    data class Success(
        val tareas: List<TareaConTags>
    ) : TareaState()

    data class Error(
        val message: String
    ) : TareaState()
}