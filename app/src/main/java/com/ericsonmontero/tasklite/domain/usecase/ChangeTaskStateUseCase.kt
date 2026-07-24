package com.ericsonmontero.tasklite.domain.usecase

import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import javax.inject.Inject

class ChangeTaskStateUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int, state: TaskState) {
        repository.updateTaskState(id, state)
    }
}