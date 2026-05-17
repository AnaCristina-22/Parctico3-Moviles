package com.example.practico3.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TareaConTags(

    @Embedded val tarea: Tarea,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = TareaTagCrossRef::class,
            parentColumn = "tareaId",
            entityColumn = "tagId"
        )
    )
    val tags: List<Tag>
)