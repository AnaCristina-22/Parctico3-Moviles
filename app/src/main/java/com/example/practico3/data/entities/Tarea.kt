package com.example.practico3.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.practico3.Generated

@Entity(tableName = "tareas")
data class Tarea(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val description: String = "",

    val tag: String = "",

    val priority: String = "Media",

    val completed: Boolean = false,

    val createdAt: Long = System.currentTimeMillis()
)