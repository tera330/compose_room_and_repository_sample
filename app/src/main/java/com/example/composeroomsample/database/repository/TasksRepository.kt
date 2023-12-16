package com.example.composeroomsample.database.repository

import com.example.composeroomsample.database.Task
import com.example.composeroomsample.database.TaskDao

class TasksRepository(private val taskDao: TaskDao) {

    suspend fun insertItem(task: Task) = taskDao.insert(task)

}