package com.example.practico3.data.entities

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["tareaId", "tagId"],
    indices = [
        Index("tareaId"),
        Index("tagId")
    ]
)
data class TareaTagCrossRef(
    val tareaId: Int,
    val tagId: Int
)