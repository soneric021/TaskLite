package com.ericsonmontero.tasklite.presentation.screens.task

import com.ericsonmontero.tasklite.domain.models.TaskDomainModel

data class TaskUiState(
    val tasks: List<TaskDomainModel> = emptyList(),
    val taskInProgress:List<TaskDomainModel> = emptyList(),
    val taskCompleted:List<TaskDomainModel> = emptyList(),
    val taskPending:List<TaskDomainModel> = emptyList(),
    val taskInProgressIsExpanded: Boolean = false,
    val taskCompletedIsExpanded: Boolean = false,
    val taskPendingIsExpanded: Boolean = false,
    val isLoading: Boolean = false
)
