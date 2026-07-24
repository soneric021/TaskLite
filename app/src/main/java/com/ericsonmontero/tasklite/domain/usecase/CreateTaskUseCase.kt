package com.ericsonmontero.tasklite.domain.usecase

import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import jakarta.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(title: String, description: String) {
        repository.insertTask(TaskDomainModel(
            title = title,
            description = description,
            state = TaskState.PENDING
        ))
    }
}