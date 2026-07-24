package com.ericsonmontero.tasklite

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.junit4.createComposeRule // Importación estándar
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.ericsonmontero.tasklite.data.models.TaskState
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel
import com.ericsonmontero.tasklite.pages.TaskListPage
import com.ericsonmontero.tasklite.presentation.navigation.TaskNavHost
import com.ericsonmontero.tasklite.presentation.navigation.TaskNavRoute
import com.ericsonmontero.tasklite.presentation.screens.task.TaskContent
import com.ericsonmontero.tasklite.presentation.screens.task.TaskUiState
import com.ericsonmontero.tasklite.ui.theme.TaskLiteTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class TaskScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
            TaskLiteTheme {
                val navController = rememberNavController()
                TaskNavHost(
                    navController = navController,
                    startDestination = TaskNavRoute.TaskScreen
                )
            }
        }
    }

    @Test
    fun createTaskTest() {
        TaskListPage(composeTestRule)
            .navigateToAddTask()
            .enterTitle("Test Task")
            .enterDescription("Test Description")
            .clickSaveTask()
            .clickCollapsablePendingTasks()
            .assertTaskExists("Test Task")
    }
    @Test
    fun deleteTaskTest(){
        TaskListPage(composeTestRule)
            .navigateToAddTask()
            .enterTitle("Test Task2")
            .enterDescription("Test Description")
            .clickSaveTask()
            .clickCollapsablePendingTasks()
            .clickTask("Test Task2")
            .clickDeleteTask()
            .clickDeleteDialog()
            .assertTaskNotExists("Test Task2")
    }

    @Test
    fun createTaskTitleErrorTest(){
        TaskListPage(composeTestRule)
            .assertNotFoundTaskDisplayed()
            .navigateToAddTask()
            .enterTitle("")
            .enterDescription("Test Description")
            .clickSaveTask()
            .assertDisplayedWithTitle("Title is required")

    }
    @Test
    fun completeTaskTest(){
        TaskListPage(composeTestRule)
            .navigateToAddTask()
            .enterTitle("Test Task3")
            .enterDescription("Test Description")
            .clickSaveTask()
            .clickCollapsablePendingTasks()
            .clickPendingState()
            .clickCollapsableInProgressTasks()
            .clickInProgressState()
            .clickCollapsableCompletedTasks()
            .assertIsCompleteStateTask()

    }
    @Test
    fun editTaskTest(){
        TaskListPage(composeTestRule)
            .navigateToAddTask()
            .enterTitle("Test Task4")
            .enterDescription("Test Description")
            .clickSaveTask()
            .clickCollapsablePendingTasks()
            .clickTask("Test Task4")
            .clearTextFieldWithTag("text_field_title")
            .clearTextFieldWithTag("text_field_description")
            .enterTitle("Test Task5")
            .enterDescription("Test Description")
            .clickSaveTask()
            .assertTaskExists("Test Task5")
    }



}