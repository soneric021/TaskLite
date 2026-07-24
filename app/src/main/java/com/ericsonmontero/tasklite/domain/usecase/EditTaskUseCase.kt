package com.ericsonmontero.tasklite.domain.usecase

import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import javax.inject.Inject

class EditTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskDomainModel: TaskDomainModel) {
        repository.updateTask(
            taskDomainModel
        )
    }
}