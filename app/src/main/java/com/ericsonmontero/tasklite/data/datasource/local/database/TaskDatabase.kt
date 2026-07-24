package com.ericsonmontero.tasklite.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ericsonmontero.tasklite.data.datasource.local.dao.TaskDao
import com.ericsonmontero.tasklite.data.models.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        const val DATABASE_NAME = "task_db"
        fun getInstance(context: Context): TaskDatabase {
            val db: TaskDatabase = Room.databaseBuilder(
                context,
                TaskDatabase::class.java,
                DATABASE_NAME,

            ).build()
            return db
        }
    }
}