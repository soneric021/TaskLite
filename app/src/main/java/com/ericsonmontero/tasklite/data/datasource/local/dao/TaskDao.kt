package com.ericsonmontero.tasklite.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ericsonmontero.tasklite.data.models.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskEntity

    @Query("UPDATE task SET state = :state WHERE id = :id")
    suspend fun updateTaskState(id: Int, state: String)

    @Query("DELETE FROM task WHERE id = :id")
    suspend fun deleteTask(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: TaskEntity)

}