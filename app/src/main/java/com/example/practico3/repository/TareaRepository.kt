package com.example.practico3.repository

import com.example.practico3.data.daos.TareaDao
import com.example.practico3.data.entities.Tag
import com.example.practico3.data.entities.Tarea
import com.example.practico3.data.entities.TareaConTags
import com.example.practico3.data.entities.TareaTagCrossRef
import kotlinx.coroutines.flow.Flow

class TareaRepository(
    private val dao: TareaDao
) {

    // Cambiado a función para exponer el Flow correctamente
    fun getAllTareas(): Flow<List<Tarea>> = dao.getAll()

    fun getTareasConTags(): Flow<List<TareaConTags>> = dao.getTareasConTags()

    fun getTags(): Flow<List<Tag>> = dao.getAllTags()

    suspend fun insert(tarea: Tarea) {
        dao.insert(tarea)
    }

    suspend fun update(tarea: Tarea) {
        dao.update(tarea)
    }

    suspend fun delete(tarea: Tarea) {
        dao.delete(tarea)
    }

    suspend fun getById(id: Int): Tarea = dao.getById(id)

    suspend fun insertTag(tag: Tag) {
        dao.insertTag(tag)
    }

    suspend fun insertTareaConTags(tarea: Tarea, tags: List<Tag>) {
        val tareaId = dao.insertTarea(tarea)
        tags.forEach { tag ->
            dao.insertCrossRef(
                TareaTagCrossRef(
                    tareaId = tareaId.toInt(),
                    tagId = tag.id
                )
            )
        }
    }

    suspend fun deleteTag(tag: Tag) {
        dao.deleteTag(tag)
    }

    suspend fun actualizarTareaConTags(tarea: Tarea, tags: List<Tag>) {
        dao.update(tarea)
        dao.deleteCrossRefsPorTarea(tarea.id)
        tags.forEach { tag ->
            dao.insertCrossRef(TareaTagCrossRef(tareaId = tarea.id, tagId = tag.id))
        }
    }

    suspend fun getTareaConTagsById(id: Int): TareaConTags = dao.getTareaConTagsById(id)

}