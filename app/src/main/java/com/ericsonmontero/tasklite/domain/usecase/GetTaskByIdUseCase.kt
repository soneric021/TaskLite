package com.ericsonmontero.tasklite.domain.usecase

import com.ericsonmontero.tasklite.data.models.Resource
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

fun interface GetTaskByIdUseCase {
     operator fun invoke(taskId: Int): Flow<Resource<TaskDomainModel>>
}