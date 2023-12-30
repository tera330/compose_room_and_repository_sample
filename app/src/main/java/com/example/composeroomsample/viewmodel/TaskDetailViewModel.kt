package com.example.composeroomsample.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeroomsample.database.repository.TasksRepository
import com.example.composeroomsample.ui.theme.navigation.Screen
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TaskDetailViewModel(
    private val tasksRepository: TasksRepository,
    taskId: Int
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val uiState: StateFlow<TaskDetailsUiState> =
        tasksRepository.getItemStream(taskId)
            .filterNotNull()
            .map {
                TaskDetailsUiState(it.toTaskDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TaskDetailsUiState()
            )
    suspend fun deleteTask() {
        tasksRepository.deleteItem(uiState.value.taskDetails.toTask())
    }
}

data class TaskDetailsUiState(
    val taskDetails: TaskDetails = TaskDetails()
)