package com.example.practico3.repository

import com.example.practico3.Generated
import com.example.practico3.data.daos.TareaDao
import com.example.practico3.data.entities.Tarea

class TareaRepository(
    private val dao: TareaDao
) {

    suspend fun insert(tarea: Tarea) {
        dao.insert(tarea)
    }

    suspend fun update(tarea: Tarea) {
        dao.update(tarea)
    }

    suspend fun delete(tarea: Tarea) {
        dao.delete(tarea)
    }

    suspend fun getAll(): List<Tarea> {
        return dao.getAll()
    }
}