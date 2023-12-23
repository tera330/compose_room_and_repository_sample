package com.example.composeroomsample.database.repository

import com.example.composeroomsample.database.Task
import com.example.composeroomsample.database.TaskDao
import kotlinx.coroutines.flow.Flow

class TasksRepository(private val taskDao: TaskDao) {

    fun getAllTaskStream(): Flow<List<Task>> = taskDao.getAllItems()

    fun getItemStream(id: Int): Flow<Task?> = taskDao.getItem(id)

    suspend fun insertItem(task: Task) = taskDao.insert(task)

    suspend fun deleteItem(task: Task) = taskDao.delete(task)

    suspend fun updateItem(task: Task) = taskDao.update(task)
}