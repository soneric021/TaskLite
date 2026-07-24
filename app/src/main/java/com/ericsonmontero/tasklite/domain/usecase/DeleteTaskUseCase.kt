package com.ericsonmontero.tasklite.domain.usecase

import com.ericsonmontero.tasklite.data.models.Resource
import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface DeleteTaskUseCase {
     operator fun invoke(id: Int): Flow<Resource<Unit>>
}
