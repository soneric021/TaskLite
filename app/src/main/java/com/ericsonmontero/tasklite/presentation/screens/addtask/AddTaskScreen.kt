package com.ericsonmontero.tasklite.presentation.screens.addtask

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onBackPressed:() -> Unit = {}
){
    val viewModel: AddTaskViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value

    AddTaskContent(
        state = state,
        onEvent = viewModel::onEvent,
        onBackPressed = onBackPressed,
        sideEffect = viewModel.events
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskContent(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    onEvent: (AddTaskEvent) -> Unit,
    state: AddTaskState,
    sideEffect: Flow<AddTaskSideEffect>
) {
    val hostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
       sideEffect.collect {
            when(it){
                is AddTaskSideEffect.ShowSnackBar -> {
                    hostState.showSnackbar(
                        message = it.message
                    )
                }
                is AddTaskSideEffect.NavigateBack -> {
                    onBackPressed()
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.testTag("top_app_bar_save_task"),
                        text = if (state.isUpdate) "Editar tarea" else "Agregar tareas"
                    )
                },
                actions = {
                    if (state.isUpdate){
                        IconButton(
                            modifier = Modifier.testTag("delete_task_button"),
                            onClick = {
                            onEvent.invoke(AddTaskEvent.ToggleDeleteDialog)
                        }) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                                tint = androidx.compose.ui.graphics.Color.Red
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            Button(onClick = {
                onEvent.invoke(AddTaskEvent.SaveTask)
            }, modifier = Modifier.fillMaxWidth().imePadding().testTag("save_task_button")) {
                Text(text = if (state.isUpdate) "Actualizar" else "Guardar")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = hostState)
        }
    ) {
            innerPadding ->
        Box(modifier.padding(innerPadding)){
            Column() {
                TextField(
                    modifier = Modifier.testTag("text_field_title"),
                    value = state.title,
                    onValueChange = {
                        onEvent.invoke(AddTaskEvent.TitleChanged(it))
                    },
                    label = { Text("Titulo") },
                )
                TextField(
                    modifier = Modifier.testTag("text_field_description"),
                    value = state.description,
                    onValueChange = {
                        onEvent.invoke(AddTaskEvent.DescriptionChanged(it))
                    },
                    label = { Text("Descripcion") },
                )
            }
        }
    }

    if (state.showDeleteDialog){
        AlertDialog(
            modifier = Modifier.testTag("delete_task_dialog"),
            onDismissRequest = {
                onEvent.invoke(AddTaskEvent.ToggleDeleteDialog)
            },
            title = {
                Text("Eliminar tarea")
            },
            text = {
                Text("¿Estas seguro de eliminar la tarea?")
            },
            confirmButton = {
                Button(
                    modifier = Modifier.testTag("delete_dialog_task_button"),
                    onClick = {
                    onEvent.invoke(AddTaskEvent.DeleteTask)
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    onEvent.invoke(AddTaskEvent.ToggleDeleteDialog)
                }) {
                    Text("Cancelar")
                }

            }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun AddTaskScreenPreview() {
    AddTaskContent(
        onEvent = {},
        state = AddTaskState(
            isUpdate = true
        ),
        sideEffect = emptyFlow()
    )
}
