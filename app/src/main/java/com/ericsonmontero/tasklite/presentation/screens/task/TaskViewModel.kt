package com.ericsonmontero.tasklite.presentation.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import com.ericsonmontero.tasklite.domain.usecase.ChangeTaskStateUseCase
import com.ericsonmontero.tasklite.domain.usecase.GetAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val changeTaskStateUseCase: ChangeTaskStateUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TaskUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: TaskEvent) {
        when (event) {
            TaskEvent.GetTasks -> {
                getTasks()
            }

            is TaskEvent.OnChangeStateTask -> {
                changeTaskState(event.taskDomainModel, event.taskState)
            }
        }
    }

    private fun changeTaskState(taskDomainModel: TaskDomainModel, state: TaskState) {
        viewModelScope.launch {
            changeTaskStateUseCase.invoke(taskDomainModel.id, state)
            getTasks()
        }
    }

    private fun getTasks() {
        getAllTasksUseCase.invoke().onEach { result ->
            _state.update {
                it.copy(
                    tasks = result
                )
            }
        }.launchIn(viewModelScope)
    }

}