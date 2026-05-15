package com.example.practico3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practico3.Generated
import com.example.practico3.data.entities.Tarea
import com.example.practico3.repository.TareaRepository
import kotlinx.coroutines.launch

class TareaViewModel(
    private val repository: TareaRepository
) : ViewModel() {

    fun insert(tarea: Tarea) {

        viewModelScope.launch {
            repository.insert(tarea)
        }
    }

    fun update(tarea: Tarea) {

        viewModelScope.launch {
            repository.update(tarea)
        }
    }

    fun delete(tarea: Tarea) {

        viewModelScope.launch {
            repository.delete(tarea)
        }
    }
}