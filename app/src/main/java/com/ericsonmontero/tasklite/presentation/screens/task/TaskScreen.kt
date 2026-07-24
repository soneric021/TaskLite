package com.ericsonmontero.tasklite.presentation.screens.task

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.CloudCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.ericsonmontero.tasklite.R
import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.models.GroupTask
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import com.ericsonmontero.tasklite.presentation.components.CollapsibleComponent
import com.ericsonmontero.tasklite.presentation.components.LoaderScreen
import com.ericsonmontero.tasklite.presentation.navigation.TaskNavRoute
import com.ericsonmontero.tasklite.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    onBackPressed:() -> Unit,
    goToEditTask: (Int?) -> Unit
){
    val viewModel: TaskViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent

    LaunchedEffect(Unit) {
        onEvent.invoke(TaskEvent.GetTasks)
    }
    if (state.isLoading && state.groupTasks.isEmpty()){
        LoaderScreen(
            Modifier.testTag("loader_screen")
        )
    } else {
        TaskContent(
            Modifier,
            state = state,
            onEvent = onEvent,
            onBackPressed = onBackPressed,
            goToEditTask = goToEditTask
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContent(
    modifier: Modifier = Modifier,
    state: TaskUiState,
    onEvent:(TaskEvent) -> Unit,
    onBackPressed: () -> Unit,
    goToEditTask: (Int?) -> Unit = {}
){
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.testTag("top_app_bar_task"),
                title = {
                    Text("Tareas", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.testTag("add_task_button"),
                onClick = {
                    goToEditTask.invoke(null)
                }
            ) {
                Text(text = "+")
            }
        }

    ) {
        Box(
            Modifier.padding(it)
        ) {
            if (state.groupTasks.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.testTag("empty_tasks"),
                        text = stringResource(R.string.tasklite_no_task_found)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .testTag("task_list")
                        .fillMaxSize(),
                ) {
                    state.groupTasks.forEachIndexed { index, groupTask ->
                        item {
                            Column(
                                Modifier
                                    .testTag(groupTask.id)
                                    .clickable {
                                        onEvent.invoke(TaskEvent.OnExpand(groupTask))
                                    }
                            ) {
                                val angle by animateFloatAsState(
                                    targetValue = if (groupTask.isExpanded) 180f else 0f,
                                    animationSpec = tween(durationMillis = 300),
                                    label = "ArrowRotation"
                                )
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        groupTask.title,
                                        Modifier.testTag("group_task_title_$index"),
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Icon(

                                        Icons.Filled.KeyboardArrowDown,
                                        contentDescription = null,
                                        modifier = Modifier.graphicsLayer {
                                            rotationZ = angle
                                        },
                                    )
                                }
                                if (groupTask.isExpanded) Divider(Modifier.padding(horizontal = 12.dp), color = MaterialTheme.colorScheme.onSurface)
                            }
                        }

                       if (groupTask.isExpanded){
                           items(groupTask.tasks.size){
                               TaskItem(
                                   modifier = Modifier.testTag("group_task_${index}_task_item_$it"),
                                   task = groupTask.tasks[it],
                                   onEvent = onEvent,
                                   onClick = {
                                       goToEditTask.invoke(groupTask.tasks[it].id)
                                   }
                               )
                           }
                       }
                    }
                }
            }
        }

    }
}

@Composable
fun TaskItem(modifier: Modifier = Modifier, task: TaskDomainModel, onEvent: (TaskEvent) -> Unit, onClick: () -> Unit = {}) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        when(task.state){
            TaskState.PENDING -> {
                IconButton(
                    modifier = Modifier.testTag("pending_task"),
                    onClick = {
                        onEvent.invoke(TaskEvent.OnChangeStateTask(task, TaskState.IN_PROGRESS))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.RadioButtonUnchecked,
                        contentDescription = "Edit"
                    )
                }
            }
            TaskState.IN_PROGRESS ->  {
                IconButton(
                    modifier = Modifier.testTag("in_progress_task"),
                    onClick = {
                        onEvent.invoke(TaskEvent.OnChangeStateTask(task, TaskState.COMPLETED))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = "Edit",
                        tint = Yellow.copy(alpha = 0.5f)
                    )
                }
            }
            TaskState.COMPLETED ->  {
                IconButton(
                    modifier = Modifier.testTag("complete_task"),
                    onClick = {
                        onEvent.invoke(TaskEvent.OnChangeStateTask(task, TaskState.PENDING))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircleOutline,
                        contentDescription = "Edit",
                        tint = Green
                    )
                }
            }
        }
       Column() {
           Text(
               text = task.title,
               style = Typography.bodyMedium,
               textDecoration = if (task.state == TaskState.COMPLETED) TextDecoration.LineThrough else TextDecoration.None
           )
           Text(text = task.description,     style = Typography.bodySmall,)
       }

    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    TaskContent(
        state = TaskUiState(
            tasks = listOf(
                TaskDomainModel(
                    title = "Task 1",
                    description = "Description 1",
                    state = TaskState.PENDING
                ),
                TaskDomainModel(
                    title = "Task 1",
                    description = "Description 1",
                    state = TaskState.IN_PROGRESS
                ),
                TaskDomainModel(
                    title = "Task 1",
                    description = "Description 1",
                    state = TaskState.COMPLETED
                ),

                ),
            taskPendingIsExpanded = true,
            taskInProgressIsExpanded = true,
            taskCompletedIsExpanded = true,
            groupTasks = listOf(
                GroupTask(
                    id = "",
                    title = "Pendientes",
                    tasks = listOf(
                        TaskDomainModel(
                            title = "Task 1",
                            description = "Description 1",
                            state = TaskState.PENDING
                        ),
                        TaskDomainModel(
                            title = "Task 1",
                            description = "Description 1",
                            state = TaskState.PENDING
                        ),
                ),
                    isExpanded = true,
                    ),
                GroupTask(
                    id = "",
                    title = "En progreso",
                    tasks = listOf(
                        TaskDomainModel(
                            title = "Task 1",
                            description = "Description 1",
                            state = TaskState.IN_PROGRESS
                        ),
                        TaskDomainModel(
                            title = "Task 1",
                            description = "Description 1",
                            state = TaskState.IN_PROGRESS
                        ),
                    ),
                    isExpanded = true,
                ),
                GroupTask(
                    id = "",
                    title = "Completadas",
                    tasks = listOf(
                        TaskDomainModel(
                            title = "Task 1",
                            description = "Description 1",
                            state = TaskState.COMPLETED
                        ),
                        TaskDomainModel(
                            title = "Task 1",
                            description = "Description 1",
                            state = TaskState.COMPLETED
                        ),
                    ),
                    isExpanded = true,
                )
                ),


        ),

        onEvent = {},
        onBackPressed = {}
    )
}
