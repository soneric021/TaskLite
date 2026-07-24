package com.ericsonmontero.tasklite.data.repository

import com.ericsonmontero.tasklite.data.datasource.local.TaskDataSource
import com.ericsonmontero.tasklite.data.mapper.toDomainModel
import com.ericsonmontero.tasklite.data.mapper.toEntity
import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import jakarta.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDataSource: TaskDataSource
): TaskRepository {
    override suspend fun getAllTasks(): List<TaskDomainModel> {
        return taskDataSource.getAllTasks().map { it.toDomainModel() }
    }

    override suspend fun getTaskById(id: Int): TaskDomainModel {
        return taskDataSource.getTaskById(id).toDomainModel()
    }

    override suspend fun updateTaskState(
        id: Int,
        state: TaskState
    ) {
        taskDataSource.updateTaskState(id, state.name)
    }

    override suspend fun deleteTask(id: Int) {
        taskDataSource.deleteTask(id)
    }

    override suspend fun insertTask(task: TaskDomainModel) {
        taskDataSource.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: TaskDomainModel) {
        taskDataSource.updateTask(task.toEntity())
    }

}