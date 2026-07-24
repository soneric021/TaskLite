package com.ericsonmontero.tasklite.di

import android.content.Context
import com.ericsonmontero.tasklite.data.datasource.local.database.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): TaskDatabase {
        return TaskDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: TaskDatabase) = database.taskDao()

}