package com.ericsonmontero.tasklite.presentation.screens.addtask

sealed class AddTaskSideEffect {
    data class ShowSnackBar(val message: String) : AddTaskSideEffect()
    object NavigateBack : AddTaskSideEffect()
}