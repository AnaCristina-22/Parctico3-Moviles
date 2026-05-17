package com.example.practico3.viewmodel

import com.example.practico3.data.entities.Tarea

sealed class TareaState {

    object Loading : TareaState()

    data class Success(
        val tareas: List<Tarea>
    ) : TareaState()

    data class Error(
        val message: String
    ) : TareaState()
}