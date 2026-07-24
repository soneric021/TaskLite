package com.ericsonmontero.tasklite.pages

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput

class AddTaskPage(composeRule: ComposeContentTestRule) : BasePage(composeRule) {
    fun clickDeleteTask() = apply {
        composeRule.onNodeWithTag("delete_task_button").performClick()

    }
    fun clickDeleteDialog(): TaskListPage {
        composeRule.onNodeWithTag("delete_dialog_task_button").performClick()
        return TaskListPage(composeRule)
    }
    fun enterTitle(title:String) = apply {
        composeRule.onNodeWithTag("text_field_title").performTextInput(title)
    }

    fun enterDescription(description:String) = apply {
        composeRule.onNodeWithTag("text_field_description").performTextInput(description)
    }
    fun clearTextFieldWithTag(tag:String) = apply {
        composeRule.onNodeWithTag(tag).performClick().performTextClearance()
    }

    fun clickSaveTask(): TaskListPage  {
        composeRule.onNodeWithTag("save_task_button").performClick()
        return TaskListPage(composeRule)
    }
}