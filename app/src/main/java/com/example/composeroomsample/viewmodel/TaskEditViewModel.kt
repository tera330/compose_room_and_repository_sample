package com.example.composeroomsample.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeroomsample.database.repository.TasksRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TaskEditViewModel(
    private val taskRepository: TasksRepository,
    taskId: (Int),
) : ViewModel() {
    var taskUiState by mutableStateOf(TaskUiState())
        private set

    init {
        viewModelScope.launch {
            taskUiState = taskRepository.getItemStream(taskId)
                .filterNotNull()
                .first()
                .toTaskUiState(true)
        }
    }

    suspend fun updateTask() {
        if (validateInput(taskUiState.taskDetails)) {
            taskRepository.updateItem(taskUiState.taskDetails.toTask())
        }
    }

    fun updateUiState(taskDetails: TaskDetails) {
        taskUiState = TaskUiState(taskDetails = taskDetails, isEntryValid = validateInput(taskDetails))
    }

    private fun validateInput(uiState: TaskDetails = taskUiState.taskDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && detail.isNotBlank()
        }
    }
}