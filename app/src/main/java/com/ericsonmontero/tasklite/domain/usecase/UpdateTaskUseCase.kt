package com.ericsonmontero.tasklite.domain.usecase

import com.ericsonmontero.tasklite.data.models.Resource
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

fun interface UpdateTaskUseCase {
     operator fun invoke(task: TaskDomainModel): Flow<Resource<Unit>>
}