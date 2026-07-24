package com.ericsonmontero.tasklite.di

import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import com.ericsonmontero.tasklite.domain.usecase.ChangeTaskStateUseCase
import com.ericsonmontero.tasklite.domain.usecase.CreateTaskUseCase
import com.ericsonmontero.tasklite.domain.usecase.DeleteTaskUseCase
import com.ericsonmontero.tasklite.domain.usecase.EditTaskUseCase
import com.ericsonmontero.tasklite.domain.usecase.GetAllTasksUseCase
import com.ericsonmontero.tasklite.domain.usecase.GetTaskByIdUseCase
import com.ericsonmontero.tasklite.domain.usecase.UpdateTaskUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetAllTasksUseCase(repository: TaskRepository): GetAllTasksUseCase  =
        GetAllTasksUseCase(repository::getAllTasks)

    @Provides
    fun provideGetTaskByIdUseCase(repository: TaskRepository): GetTaskByIdUseCase =
        GetTaskByIdUseCase(repository::getTaskById)

    @Provides
    fun provideChangeTaskStateUseCase(repository: TaskRepository): ChangeTaskStateUseCase =
        ChangeTaskStateUseCase(repository::updateTaskState)

    @Provides
    fun provideDeleteTaskUseCase(repository: TaskRepository): DeleteTaskUseCase =
        DeleteTaskUseCase(repository::deleteTask)

    @Provides
    fun provideEditTaskUseCase(repository: TaskRepository): EditTaskUseCase =
        EditTaskUseCase(repository::updateTask)

    @Provides
    fun provideUpdateTaskUseCase(repository: TaskRepository): UpdateTaskUseCase = UpdateTaskUseCase(repository::updateTask)
}