package com.ericsonmontero.tasklite.domain.models

import com.ericsonmontero.tasklite.data.models.TaskState

data class GroupTask(
    val id:String,
    val title:String,
    val tasks: List<TaskDomainModel>,
    val isExpanded: Boolean = false,
    val state: TaskState = TaskState.PENDING
)
