package com.example.composeroomsample.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeroomsample.database.Task
import com.example.composeroomsample.database.repository.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(private val tasksRepository: TasksRepository): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    // 追加
    private val taskDetailViewModel = TaskDetailViewModel(tasksRepository, 0)

    val homeUiState: StateFlow<HomeUiState> =
        tasksRepository.getAllTaskStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    suspend fun deleteItemsByIds(idsList: List<Int>) {
        tasksRepository.deleteItemsByIds(idsList)
    }
}
data class HomeUiState(
    val taskList: List<Task> = listOf(),
)



