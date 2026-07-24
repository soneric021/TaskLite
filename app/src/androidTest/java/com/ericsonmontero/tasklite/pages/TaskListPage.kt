package com.ericsonmontero.tasklite.pages

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

class TaskListPage(composeRule: ComposeContentTestRule) : BasePage(composeRule) {
    fun navigateToAddTask(): AddTaskPage {
        composeRule.onNodeWithTag("add_task_button").performClick()
        return AddTaskPage(composeRule)
    }
    fun clickTask(title: String): AddTaskPage {
        composeRule.onNodeWithText(title).performClick()
        return AddTaskPage(composeRule)
    }
    fun clickInProgressState() = apply {
        composeRule.onNodeWithTag("in_progress_task").performClick()
    }
    fun clickPendingState() = apply {
        composeRule.onNodeWithTag("pending_task").performClick()
    }
    fun clickCompleteState() = apply {
        composeRule.onNodeWithTag("complete_task").performClick()
    }
    fun assertIsCompleteStateTask() = apply {
        composeRule.onNodeWithTag("complete_task").assertExists()
    }

    fun assertNotFoundTaskDisplayed() = apply {
        composeRule.onNodeWithTag("empty_tasks").assertExists()
    }
    fun clickCollapsablePendingTasks() = apply {
        composeRule.onNodeWithTag("pending_tasks").performClick()
    }
    fun clickCollapsableInProgressTasks() = apply {
        composeRule.onNodeWithTag("in_progress_tasks").performClick()
    }
    @OptIn(ExperimentalTestApi::class)
    fun clickCollapsableCompletedTasks() = apply {
        composeRule.onNodeWithTag("completed_tasks").performClick()
    }


}