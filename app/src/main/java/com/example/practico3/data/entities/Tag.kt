package com.example.practico3.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.practico3.Generated

@Entity(tableName = "tags")
data class Tag(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String
)