package com.ericsonmontero.tasklite.data.datasource.local

import com.ericsonmontero.tasklite.data.models.TaskEntity

interface TaskDataSource {
    suspend fun getAllTasks(): List<TaskEntity>
    suspend fun getTaskById(id: Int): TaskEntity
    suspend fun updateTaskState(id: Int, state: String)
    suspend fun deleteTask(id: Int)
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
}