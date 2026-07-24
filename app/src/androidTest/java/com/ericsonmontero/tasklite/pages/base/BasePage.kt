package com.ericsonmontero.tasklite.pages.base

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithText

open class BasePage(protected val composeRule: ComposeContentTestRule) {
    fun assertTaskExists(title:String) = apply {
        composeRule.onNodeWithText(title).assertExists()
    }

    fun assertTaskNotExists(title:String) = apply {
        composeRule.onNodeWithText(title).assertDoesNotExist()
    }
    fun assertNoDisplayedWithTitle(title:String) = apply {
        composeRule.onNodeWithText(title).assertIsNotDisplayed()
    }
    fun assertDisplayedWithTitle(title:String) = apply {
        composeRule.onNodeWithText(title).assertIsDisplayed()
    }

}