package com.ericsonmontero.tasklite.presentation.navigation

import kotlinx.serialization.Serializable

sealed class TaskNavRoute {
   @Serializable
   data class EditTaskScreen(val taskId: Int? = null) : TaskNavRoute()

   @Serializable
   data object TaskScreen : TaskNavRoute()
}