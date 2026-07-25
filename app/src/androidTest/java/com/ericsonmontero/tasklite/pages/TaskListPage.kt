package com.ericsonmontero.tasklite.pages

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ericsonmontero.tasklite.pages.base.BasePage
import com.ericsonmontero.tasklite.presentation.TestTags

class TaskListPage(composeRule: ComposeContentTestRule) : BasePage(composeRule) {
    fun navigateToAddTask(): AddTaskPage {
        composeRule.onNodeWithTag(TestTags.ADD_TASK_BUTTON).performClick()
        return AddTaskPage(composeRule)
    }
    fun clickTask(title: String): AddTaskPage {
        composeRule.onNodeWithText(title).performClick()
        return AddTaskPage(composeRule)
    }
    fun clickInProgressState() = apply {
        composeRule.onNodeWithTag(TestTags.IN_PROGRESS_TASK).performClick()
    }
    fun clickPendingState() = apply {
        composeRule.onNodeWithTag(TestTags.PENDING_TASK).performClick()
    }
    fun clickCompleteState() = apply {
        composeRule.onNodeWithTag(TestTags.COMPLETED_TASK).performClick()
    }
    fun assertIsCompleteStateTask() = apply {
        composeRule.onNodeWithTag(TestTags.COMPLETED_TASK).assertExists()
    }

    fun assertNotFoundTaskDisplayed() = apply {
        composeRule.onNodeWithTag(TestTags.EMPTY_TASKS).assertExists()
    }

    fun clickCollapsablePendingTasks() = apply {
        composeRule.onNodeWithTag(TestTags.PENDING_TASKS).performClick()
    }
    fun clickCollapsableInProgressTasks() = apply {
        composeRule.onNodeWithTag(TestTags.IN_PROGRESS_TASKS).performClick()
    }

    fun clickCollapsableCompletedTasks() = apply {
        composeRule.onNodeWithTag(TestTags.COMPLETED_TASKS).performClick()
    }


}