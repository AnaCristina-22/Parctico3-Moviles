package com.example.practico3.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practico3.Generated
import com.example.practico3.data.daos.TareaDao
import com.example.practico3.data.entities.Tarea

@Database(
    entities = [Tarea::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tareaDao(): TareaDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tarea_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}