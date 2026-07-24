package com.ericsonmontero.tasklite.domain.usecase

import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import jakarta.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteTask(id)
    }
}
