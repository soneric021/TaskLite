package com.ericsonmontero.tasklite.presentation.screens.task

import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.models.GroupTask
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel

sealed class TaskEvent {
    data class OnChangeStateTask(val taskDomainModel: TaskDomainModel, val taskState: TaskState) : TaskEvent()
    object GetTasks : TaskEvent()
    data class OnExpand(val groupTask: GroupTask) : TaskEvent()
}