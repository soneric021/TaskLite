package com.ericsonmontero.tasklite.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dagger.Module
import dagger.Provides
import com.ericsonmontero.tasklite.data.datasource.local.database.TaskDatabase
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
class TestAppModule {
    @Provides
    fun provideInMemoryDb(): TaskDatabase {
        val context = ApplicationProvider.getApplicationContext<Context>()
        return Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
    @Provides
    fun provideTaskDao(db: TaskDatabase) = db.taskDao()

}