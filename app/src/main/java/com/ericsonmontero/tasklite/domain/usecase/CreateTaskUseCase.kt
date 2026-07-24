package com.ericsonmontero.tasklite.domain.usecase

import com.ericsonmontero.tasklite.data.models.Resource
import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
     fun invoke(title: String, description: String): Flow<Resource<Unit>> = repository.insertTask(TaskDomainModel(title = title, description = description, state = TaskState.PENDING))
}