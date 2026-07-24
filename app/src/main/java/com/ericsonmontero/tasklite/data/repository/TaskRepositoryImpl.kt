package com.ericsonmontero.tasklite.data.repository

import com.ericsonmontero.tasklite.data.datasource.local.TaskDataSource
import com.ericsonmontero.tasklite.data.mapper.toDomainModel
import com.ericsonmontero.tasklite.data.mapper.toEntity
import com.ericsonmontero.tasklite.data.models.Resource
import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDataSource: TaskDataSource
): TaskRepository {
    override fun getAllTasks(): Flow<Resource<List<TaskDomainModel>>> = flow {
        emit(Resource.Loading())
        try {
            val tasks = taskDataSource.getAllTasks().map { it.toDomainModel() }
            emit(Resource.Success(tasks))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override fun getTaskById(id: Int): Flow<Resource<TaskDomainModel>> = flow {
         try {
            val task = taskDataSource.getTaskById(id).toDomainModel()
            emit(Resource.Success(task))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override  fun updateTaskState(
        id: Int,
        state: TaskState
    ): Flow<Resource<Unit>> = flow {
         try {
            taskDataSource.updateTaskState(id, state.name)
            emit( Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override fun deleteTask(id: Int): Flow<Resource<Unit>> = flow {
        try {
            taskDataSource.deleteTask(id)
            emit( Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override fun insertTask(task: TaskDomainModel): Flow<Resource<Unit>> = flow {
        try {
            taskDataSource.insertTask(task.toEntity())
            emit( Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override fun updateTask(task: TaskDomainModel): Flow<Resource<Unit>> = flow {
        try {
            taskDataSource.updateTask(task.toEntity())
            emit( Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }
}
