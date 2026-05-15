package com.example.practico3.data.daos

import androidx.room.*
import com.example.practico3.Generated
import com.example.practico3.data.entities.Tarea

@Dao
interface TareaDao {

    @Insert
    suspend fun insert(tarea: Tarea)

    @Update
    suspend fun update(tarea: Tarea)

    @Delete
    suspend fun delete(tarea: Tarea)

    @Query("SELECT * FROM tareas")
    suspend fun getAll(): List<Tarea>
}