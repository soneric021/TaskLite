package com.ericsonmontero.tasklite.di

import com.ericsonmontero.tasklite.data.datasource.local.TaskDataSource
import com.ericsonmontero.tasklite.data.datasource.local.TaskDataSourceImpl
import com.ericsonmontero.tasklite.data.repository.TaskRepositoryImpl
import com.ericsonmontero.tasklite.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindTaskDatasource(
        taskDatasourceImpl: TaskDataSourceImpl
    ): TaskDataSource
}