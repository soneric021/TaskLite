package com.ericsonmontero.tasklite.domain.usecase

import com.ericsonmontero.tasklite.data.models.Resource
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface EditTaskUseCase {
     operator fun invoke(taskDomainModel: TaskDomainModel) : Flow<Resource<Unit>>
}