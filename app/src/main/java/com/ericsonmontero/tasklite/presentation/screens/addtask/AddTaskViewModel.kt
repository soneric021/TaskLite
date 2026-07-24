package com.ericsonmontero.tasklite.presentation.screens.addtask

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ericsonmontero.tasklite.domain.usecase.CreateTaskUseCase
import com.ericsonmontero.tasklite.domain.usecase.DeleteTaskUseCase
import com.ericsonmontero.tasklite.domain.usecase.GetTaskByIdUseCase
import com.ericsonmontero.tasklite.domain.usecase.UpdateTaskUseCase
import com.ericsonmontero.tasklite.presentation.navigation.TaskNavRoute
import com.ericsonmontero.tasklite.presentation.screens.task.TaskUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val createTaskUseCase: CreateTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
): ViewModel() {
    private val _state = MutableStateFlow(AddTaskState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<AddTaskSideEffect>(
        extraBufferCapacity = 1
    )
    val events = _events.asSharedFlow()

    init {
        getRoute()
    }

    private fun getRoute() {
        val taskId = savedStateHandle.toRoute<TaskNavRoute.EditTaskScreen>()
        getTaskId(taskId.taskId)
    }

    private fun getTaskId(taskId: Int?) {
        taskId?.let {
            viewModelScope.launch {
                val task = getTaskByIdUseCase.invoke(taskId)
                _state.update {
                    it.copy(
                        title = task.title,
                        description = task.description,
                        taskDomainModel = task,
                        isUpdate = true
                    )
                }
            }
        }
    }



    fun onEvent(event: AddTaskEvent){
        when(event){
            is AddTaskEvent.TitleChanged -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }
            is AddTaskEvent.DescriptionChanged -> {
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }
            AddTaskEvent.SaveTask -> {
                if (state.value.isUpdate){
                    updateTask()
                }else{
                    saveTask()
                }
            }

            AddTaskEvent.DeleteTask -> {
                deleteTask()
            }

            AddTaskEvent.ToggleDeleteDialog -> {
                _state.update {
                    it.copy(
                        showDeleteDialog = !it.showDeleteDialog
                    )
                }
            }
        }

    }

    private fun deleteTask() {
        viewModelScope.launch {
            deleteTaskUseCase.invoke(state.value.taskDomainModel?.id ?: return@launch)
            _events.emit(AddTaskSideEffect.NavigateBack)
        }
    }

    private fun updateTask() {
        val taskDomainModel = state.value.taskDomainModel ?: return

        viewModelScope.launch {
            updateTaskUseCase.invoke(
                taskDomainModel.copy(
                    title = state.value.title,
                    description = state.value.description
                )
            )
            _events.emit(AddTaskSideEffect.NavigateBack)
        }
    }

    private fun saveTask() {
        if (state.value.title.isBlank()){
            viewModelScope.launch {
                _events.emit(AddTaskSideEffect.ShowSnackBar("Title is required"))
            }
            return
        }
        if (state.value.description.isBlank()){
            viewModelScope.launch {
                _events.emit(AddTaskSideEffect.ShowSnackBar("Description is required"))
            }
            return
        }
        viewModelScope.launch {
            createTaskUseCase.invoke(state.value.title, state.value.description)
            _events.emit(AddTaskSideEffect.NavigateBack)
        }
    }
}