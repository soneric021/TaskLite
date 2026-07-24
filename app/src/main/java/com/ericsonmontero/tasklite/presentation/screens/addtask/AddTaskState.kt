package com.ericsonmontero.tasklite.presentation.screens.addtask

import com.ericsonmontero.tasklite.domain.models.TaskDomainModel

data class AddTaskState(
    val title:String = "",
    val description:String = "",
    val taskDomainModel: TaskDomainModel? = null,
    val isUpdate:Boolean = false,
    val showDeleteDialog: Boolean = false
)
