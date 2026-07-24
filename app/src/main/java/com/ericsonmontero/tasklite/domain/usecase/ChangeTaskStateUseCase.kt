package com.ericsonmontero.tasklite.domain.usecase

import com.ericsonmontero.tasklite.data.models.Resource
import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface ChangeTaskStateUseCase  {
     operator fun invoke(id: Int, state: TaskState) : Flow<Resource<Unit>>
}