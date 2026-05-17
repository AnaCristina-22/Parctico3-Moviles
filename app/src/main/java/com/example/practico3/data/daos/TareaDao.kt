package com.example.practico3.data.daos

import androidx.room.*
import com.example.practico3.Generated
import com.example.practico3.data.entities.Tag
import com.example.practico3.data.entities.Tarea
import com.example.practico3.data.entities.TareaConTags
import com.example.practico3.data.entities.TareaTagCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {

    @Insert
    suspend fun insert(tarea: Tarea)

    @Insert
    suspend fun insertTag(tag: Tag)

    @Insert
    suspend fun insertCrossRef(crossRef: TareaTagCrossRef)

    @Insert
    suspend fun insertTarea(tarea: Tarea): Long

    @Update
    suspend fun update(tarea: Tarea)

    @Delete
    suspend fun delete(tarea: Tarea)

    // NUEVO: Obtener una tarea con sus etiquetas por ID para la pantalla de edición
    @Transaction
    @Query("SELECT * FROM tareas WHERE id = :id")
    suspend fun getTareaConTagsById(id: Int): TareaConTags

    // NUEVO: Eliminar asociaciones de etiquetas viejas para poder actualizarlas
    @Query("DELETE FROM TareaTagCrossRef WHERE tareaId = :tareaId")
    suspend fun deleteCrossRefsPorTarea(tareaId: Int)

    @Query("SELECT * FROM tareas")
    fun getAll(): Flow<List<Tarea>>

    @Query("SELECT * FROM tareas WHERE id = :id")
    suspend fun getById(id: Int): Tarea

    @Transaction
    @Query("SELECT * FROM tareas")
    fun getTareasConTags(): Flow<List<TareaConTags>>

    @Transaction
    @Query("""
        SELECT * FROM tareas 
        WHERE id IN (
            SELECT tareaId FROM TareaTagCrossRef 
            WHERE tagId = :tagId
        )
    """)

    fun getTareasPorTag(tagId: Int): Flow<List<TareaConTags>>

    @Query("SELECT * FROM tags")
    fun getAllTags(): Flow<List<Tag>>

    @Delete
    suspend fun deleteTag(tag: Tag)


}