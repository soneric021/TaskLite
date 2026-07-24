package com.ericsonmontero.tasklite.presentation.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericsonmontero.tasklite.data.models.Resource
import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import com.ericsonmontero.tasklite.domain.usecase.ChangeTaskStateUseCase
import com.ericsonmontero.tasklite.domain.usecase.GetAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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

            is TaskEvent.OnExpand -> {
                when(event.taskState){
                    TaskState.PENDING -> {
                        _state.update {
                            it.copy(
                                taskPendingIsExpanded = event.isExpanded
                            )
                        }
                    }
                    TaskState.IN_PROGRESS -> {
                        _state.update {
                            it.copy(
                                taskInProgressIsExpanded = event.isExpanded
                            )
                        }
                    }
                    TaskState.COMPLETED ->  {
                        _state.update {
                            it.copy(
                                taskCompletedIsExpanded = event.isExpanded
                            )
                        }
                    }
                }
            }
        }
    }

    private fun changeTaskState(taskDomainModel: TaskDomainModel, state: TaskState) {
        changeTaskStateUseCase.invoke(taskDomainModel.id, state).onEach {
            when(it){
                is Resource.Error -> {

                }
                is Resource.Loading ->{

                }
                is Resource.Success<*> -> {
                    getTasks()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTasks() {
        getAllTasksUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            tasks = result.data,
                            taskInProgress = result.data.filter { it.state == TaskState.IN_PROGRESS },
                            taskCompleted = result.data.filter { it.state == TaskState.COMPLETED },
                            taskPending = result.data.filter { it.state == TaskState.PENDING },
                            isLoading = false
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
                is Resource.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

}