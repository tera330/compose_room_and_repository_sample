package com.example.composeroomsample

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.composeroomsample.database.Task
import com.example.composeroomsample.database.TaskDatabase
import com.example.composeroomsample.database.repository.TasksRepository

class TaskEntryViewModel(private val tasksRepository: TasksRepository): ViewModel() {

    var taskUiState by mutableStateOf(TaskUiState())

    private fun validateInput(uiState: TaskDetails = taskUiState.taskDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && detail.isNotBlank()
        }
    }

    fun updateUiState(taskDetails: TaskDetails) {
        taskUiState =
            TaskUiState(taskDetails = taskDetails, isEntryValid = validateInput(taskDetails))
    }

    suspend fun saveItem() {
        Log.d("result", validateInput().toString())
        if (validateInput()) {
            tasksRepository.insertItem(taskUiState.taskDetails.toTask())
        }
    }
}

fun TaskDetails.toTask(): Task = Task(
    id = id,
    title = title,
    detail = detail
)

data class TaskUiState(
    val taskDetails: TaskDetails = TaskDetails(),
    val isEntryValid: Boolean = false
)

// 保存用データ（DAO）と表示用データで形式が変わる場合に定義
// アーキテクチャによって用検討
data class TaskDetails(
    val id: Int = 0,
    val title: String = "",
    val detail: String = "",
)
