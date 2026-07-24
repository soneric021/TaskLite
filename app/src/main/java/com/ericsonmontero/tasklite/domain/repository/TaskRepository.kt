package com.ericsonmontero.tasklite.domain.repository

import com.ericsonmontero.tasklite.data.models.Resource
import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<Resource<List<TaskDomainModel>>>
    fun getTaskById(id: Int): Flow<Resource<TaskDomainModel>>
    fun updateTaskState(id: Int, state: TaskState): Flow<Resource<Unit>>
    fun deleteTask(id: Int): Flow<Resource<Unit>>
    fun insertTask(task: TaskDomainModel): Flow<Resource<Unit>>
    fun updateTask(task: TaskDomainModel): Flow<Resource<Unit>>
}
