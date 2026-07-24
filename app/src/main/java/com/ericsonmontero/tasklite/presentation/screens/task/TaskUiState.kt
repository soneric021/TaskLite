package com.ericsonmontero.tasklite.presentation.screens.task

import com.ericsonmontero.tasklite.domain.models.TaskDomainModel

data class TaskUiState(
    val tasks:List<TaskDomainModel> = listOf()
)
