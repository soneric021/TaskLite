package com.ericsonmontero.tasklite.presentation.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericsonmontero.tasklite.data.models.Resource
import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.models.GroupTask
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
                _state.update {
                    it.copy(
                        groupTasks = it.groupTasks.map { groupTask ->
                            if (groupTask.state == event.groupTask.state) {
                                groupTask.copy(isExpanded = !groupTask.isExpanded)
                            } else {
                                groupTask
                            }
                        },
                        taskPendingIsExpanded = if (event.groupTask.state == TaskState.PENDING) !it.taskPendingIsExpanded else it.taskPendingIsExpanded,
                        taskInProgressIsExpanded = if (event.groupTask.state == TaskState.IN_PROGRESS) !it.taskInProgressIsExpanded else it.taskInProgressIsExpanded,
                        taskCompletedIsExpanded = if (event.groupTask.state == TaskState.COMPLETED) !it.taskCompletedIsExpanded else it.taskCompletedIsExpanded
                    )
                }
            }
        }
    }

    private fun changeTaskState(taskDomainModel: TaskDomainModel, state: TaskState) {
        changeTaskStateUseCase.invoke(taskDomainModel.id, state).onEach {
            when (it) {
                is Resource.Error -> {

                }

                is Resource.Loading -> {

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
                            groupTasks = result.data.groupBy { it.state }.map { (state, tasks) ->
                                when (state) {
                                    TaskState.PENDING -> GroupTask(
                                        id = "pending_tasks",
                                        title = "Pendientes",
                                        tasks = tasks,
                                        state = state,
                                        isExpanded = _state.value.taskPendingIsExpanded
                                    )

                                    TaskState.IN_PROGRESS -> GroupTask(
                                        id = "in_progress_tasks",
                                        title =
                                            "En progreso",
                                        tasks = tasks,
                                        state = state,
                                        isExpanded = _state.value.taskInProgressIsExpanded
                                    )

                                    TaskState.COMPLETED -> GroupTask(
                                        id = "completed_tasks",
                                        title = "Completadas (${tasks.size})",
                                        tasks = tasks,
                                        state = state,
                                        isExpanded = _state.value.taskCompletedIsExpanded
                                    )
                                }
                            }.sortedBy {
                                it.state
                            }
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