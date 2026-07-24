package com.ericsonmontero.tasklite.presentation.screens.addtask

sealed class AddTaskEvent {
    data class TitleChanged(val title: String) : AddTaskEvent()
    data class DescriptionChanged(val description: String) : AddTaskEvent()
    object SaveTask : AddTaskEvent()
    object ToggleDeleteDialog : AddTaskEvent()
    object DeleteTask : AddTaskEvent()
}