package com.ericsonmontero.tasklite.presentation.screens.addtask

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ericsonmontero.tasklite.R
import com.ericsonmontero.tasklite.presentation.TestTags
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(TestTags.TOP_APP_BAR_SAVE_TASK),
                        text = if (state.isUpdate) stringResource(R.string.tasklite_edit_task) else stringResource(
                            R.string.tasklite_add_task
                        ),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPressed()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (state.isUpdate){
                        IconButton(
                            modifier = Modifier.testTag(TestTags.TASK_ITEM_DELETE_BUTTON),
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
            }, modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .testTag(TestTags.SAVE_TASK_BUTTON)
                .padding(12.dp), contentPadding = PaddingValues(16.dp)) {
                Text(text = if (state.isUpdate) stringResource(R.string.tasklite_update) else stringResource(
                    R.string.tasklite_save
                ), style = MaterialTheme.typography.titleMedium)
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = hostState)
        }
    ) {
            innerPadding ->
        Box(modifier
            .padding(innerPadding)
            .fillMaxSize()){
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.padding(16.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .testTag(TestTags.TEXT_FIELD_TITLE)
                        .fillMaxWidth(),
                    value = state.title,
                    onValueChange = {
                        onEvent.invoke(AddTaskEvent.TitleChanged(it))
                    },
                    isError = state.titleIsError,
                    label = { Text(stringResource(R.string.tasklite_title)) },
                )
                Spacer(modifier = Modifier.padding(4.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .testTag(TestTags.TEXT_FIELD_DESCRIPTION)
                        .fillMaxWidth(),
                    value = state.description,
                    isError = state.descriptionIsError,
                    onValueChange = {
                        onEvent.invoke(AddTaskEvent.DescriptionChanged(it))
                    },
                    label = { Text(stringResource(R.string.tasklite_description)) },
                )
            }
        }
    }

    if (state.showDeleteDialog){
        AlertDialog(
            modifier = Modifier,
            onDismissRequest = {
                onEvent.invoke(AddTaskEvent.ToggleDeleteDialog)
            },
            title = {
                Text(stringResource(R.string.tasklite_delete_task))
            },
            text = {
                Text(stringResource(R.string.tasklite_sure_to_delete_task))
            },
            confirmButton = {
                Button(
                    modifier = Modifier.testTag(TestTags.TASK_ITEM_EDIT_DIALOG_DELETE_CONFIRMATION_BUTTON),
                    onClick = {
                    onEvent.invoke(AddTaskEvent.DeleteTask)
                }) {
                    Text(stringResource(R.string.tasklite_delete))
                }
            },
            dismissButton = {
                OutlinedButton(
                    modifier = Modifier.testTag(TestTags.TASK_ITEM_EDIT_DIALOG_CANCEL_BUTTON),
                    onClick = {
                    onEvent.invoke(AddTaskEvent.ToggleDeleteDialog)
                }) {
                    Text(stringResource(R.string.tasklite_cancel))
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
