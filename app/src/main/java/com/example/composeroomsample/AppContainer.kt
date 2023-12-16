package com.example.composeroomsample

import android.content.Context
import com.example.composeroomsample.database.TaskDatabase
import com.example.composeroomsample.database.repository.TasksRepository

/*
interface AppContainer {
    val tasksRepository: TasksRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val tasksRepository: TasksRepository by lazy {
        TasksRepository(TaskDatabase.getDatabase(context).taskDao())
    }
}
 */