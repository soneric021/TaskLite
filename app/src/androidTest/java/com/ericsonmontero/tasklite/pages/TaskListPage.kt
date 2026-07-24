package com.ericsonmontero.tasklite.pages

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
    fun clickStateTaskByTag(tag: String) = apply {
        composeRule.onNodeWithTag(tag).performClick()
    }
    fun assertIsCompleteStateTask() = apply {
        composeRule.onNodeWithTag("complete_task").assertExists()
    }
    fun assertNotFoundTaskDisplayed() = apply {
        composeRule.onNodeWithTag("empty_tasks").assertExists()
    }


}