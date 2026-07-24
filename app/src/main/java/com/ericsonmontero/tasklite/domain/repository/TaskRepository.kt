package com.ericsonmontero.tasklite.domain.repository

import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel

interface TaskRepository {
    suspend fun getAllTasks(): List<TaskDomainModel>
    suspend fun getTaskById(id: Int): TaskDomainModel
    suspend fun updateTaskState(id: Int, state: TaskState)
    suspend fun deleteTask(id: Int)
    suspend fun insertTask(task: TaskDomainModel)
    suspend fun updateTask(task: TaskDomainModel)
}