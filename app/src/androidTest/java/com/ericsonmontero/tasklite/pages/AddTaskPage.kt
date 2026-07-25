package com.ericsonmontero.tasklite.pages

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.ericsonmontero.tasklite.pages.base.BasePage
import com.ericsonmontero.tasklite.presentation.TestTags
import com.ericsonmontero.tasklite.presentation.TestTags.TEXT_FIELD_DESCRIPTION

class AddTaskPage(composeRule: ComposeContentTestRule) : BasePage(composeRule) {
    fun clickDeleteTask() = apply {
        composeRule.onNodeWithTag(TestTags.TASK_ITEM_DELETE_BUTTON).performClick()

    }
    fun clickDeleteDialog(): TaskListPage {
        composeRule.onNodeWithTag(TestTags.TASK_ITEM_EDIT_DIALOG_DELETE_CONFIRMATION_BUTTON).performClick()
        return TaskListPage(composeRule)
    }
    fun enterTitle(title:String) = apply {
        composeRule.onNodeWithTag(TestTags.TEXT_FIELD_TITLE).performTextInput(title)
    }

    fun enterDescription(description:String) = apply {
        composeRule.onNodeWithTag(TestTags.TEXT_FIELD_DESCRIPTION).performTextInput(description)
    }

    fun clearTitleTextField() = apply {
        composeRule.onNodeWithTag(TestTags.TEXT_FIELD_TITLE).performClick().performTextClearance()
    }
    fun clearDescriptionTextField() = apply {
        composeRule.onNodeWithTag(TEXT_FIELD_DESCRIPTION).performClick().performTextClearance()
    }

    fun clickSaveTask(): TaskListPage  {
        composeRule.onNodeWithTag(TestTags.SAVE_TASK_BUTTON).performClick()
        return TaskListPage(composeRule)
    }
}