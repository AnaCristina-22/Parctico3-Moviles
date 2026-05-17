package com.example.practico3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practico3.data.entities.Tag
import com.example.practico3.data.entities.Tarea
import com.example.practico3.repository.TareaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TareaViewModel(
    private val repository: TareaRepository
) : ViewModel() {

    private val _state = MutableStateFlow<TareaState>(TareaState.Loading)
    val state: StateFlow<TareaState> = _state

    val tareasConTags = repository.getTareasConTags()
    val tags = repository.getTags()

    init {
        observarTareas()
    }

    private fun observarTareas() {
        viewModelScope.launch {
            // Recolectamos desde la nueva función del repositorio
            repository.getAllTareas().collect { lista ->
                _state.value = TareaState.Success(lista)
            }
        }
    }

    fun insert(tarea: Tarea) {
        viewModelScope.launch {
            try {
                repository.insert(tarea)
            } catch (e: Exception) {
                _state.value = TareaState.Error(e.message ?: "Error al insertar")
            }
        }
    }

    fun update(tarea: Tarea) {
        viewModelScope.launch {
            try {
                repository.update(tarea)
            } catch (e: Exception) {
                _state.value = TareaState.Error(e.message ?: "Error al actualizar")
            }
        }
    }

    fun delete(tarea: Tarea) {
        viewModelScope.launch {
            try {
                repository.delete(tarea)
            } catch (e: Exception) {
                _state.value = TareaState.Error(e.message ?: "Error al eliminar")
            }
        }
    }

    fun getTareaById(id: Int, onResult: (Tarea) -> Unit) {
        viewModelScope.launch {
            try {
                val tarea = repository.getById(id)
                onResult(tarea)
            } catch (e: Exception) {
                _state.value = TareaState.Error(e.message ?: "Error al obtener tarea")
            }
        }
    }

    fun updateTarea(tarea: Tarea) {
        viewModelScope.launch {
            try {
                repository.update(tarea)
            } catch (e: Exception) {
                _state.value = TareaState.Error(e.message ?: "Error al actualizar tarea")
            }
        }
    }

    fun crearTag(nombre: String) {
        viewModelScope.launch {
            try {
                repository.insertTag(Tag(name = nombre))
            } catch (e: Exception) {
                _state.value = TareaState.Error(e.message ?: "Error al crear etiqueta")
            }
        }
    }

    fun crearTareaConTags(tarea: Tarea, tags: List<Tag>) {
        viewModelScope.launch {
            try {
                repository.insertTareaConTags(tarea, tags)
            } catch (e: Exception) {
                _state.value = TareaState.Error(e.message ?: "Error al guardar tarea con tags")
            }
        }
    }

    fun deleteTag(tag: Tag) {
        viewModelScope.launch {
            try {
                repository.deleteTag(tag)
            } catch (e: Exception) {
                _state.value = TareaState.Error(e.message ?: "Error al eliminar etiqueta")
            }
        }
    }
}