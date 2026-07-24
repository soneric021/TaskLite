package com.ericsonmontero.tasklite.data.datasource.local

import com.ericsonmontero.tasklite.data.datasource.local.dao.TaskDao
import com.ericsonmontero.tasklite.data.models.TaskEntity
import javax.inject.Inject

class TaskDataSourceImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskDataSource {
    override suspend fun getAllTasks(): List<TaskEntity> {
        return taskDao.getAllTasks()
    }

    override suspend fun getTaskById(id: Int): TaskEntity {
        return taskDao.getTaskById(id)
    }

    override suspend fun updateTaskState(id: Int, state: String) {
        taskDao.updateTaskState(id, state)
    }

    override suspend fun deleteTask(id: Int) {
        taskDao.deleteTask(id)
    }

    override suspend fun insertTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    override suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }

}