package com.ericsonmontero.tasklite.domain.models

import com.ericsonmontero.tasklite.data.models.TaskState

data class TaskDomainModel(
    val id: Int = 0,
    val title: String,
    val description: String,
    val state: TaskState
)
