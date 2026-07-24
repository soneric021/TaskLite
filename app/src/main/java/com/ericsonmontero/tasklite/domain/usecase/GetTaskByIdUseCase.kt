package com.ericsonmontero.tasklite.domain.usecase

import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import jakarta.inject.Inject

class GetTaskByIdUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: Int): TaskDomainModel {
        return repository.getTaskById(taskId)
    }

}