package com.example.practico3.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.practico3.Generated

@Entity(tableName = "tareas")
data class Tarea(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val priority: String = "Media",
    val completed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val dueDate: String = ""
)
