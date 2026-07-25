package com.ericsonmontero.tasklite.presentation.screens.task

import com.ericsonmontero.tasklite.domain.models.GroupTask
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel

data class TaskUiState(
    val tasks: List<TaskDomainModel> = emptyList(),
    val groupTasks: List<GroupTask> = emptyList(),
    val taskInProgressIsExpanded: Boolean = true,
    val taskCompletedIsExpanded: Boolean = false,
    val taskPendingIsExpanded: Boolean = true,
    val isLoading: Boolean = false
)
