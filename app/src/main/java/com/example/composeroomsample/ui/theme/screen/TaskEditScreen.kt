package com.example.composeroomsample.ui.theme.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeroomsample.database.TaskDatabase
import com.example.composeroomsample.database.repository.TasksRepository
import com.example.composeroomsample.viewmodel.TaskEditViewModel
import kotlinx.coroutines.launch

@Composable
fun TaskEditScreen(
    taskId: Int,
    onSaveClick: () -> Unit
) {
    val repository = TasksRepository(TaskDatabase.getDatabase(LocalContext.current).taskDao())
    val viewModel: TaskEditViewModel = viewModel {
        TaskEditViewModel(repository, taskId)
    }
    val coroutineScope = rememberCoroutineScope()

    TaskEntryBody(
        headerText = "タスクを編集",
        taskUiState = viewModel.taskUiState,
        onTaskValueChange = viewModel::updateUiState,
        onSaveClick = {
            coroutineScope.launch {
                viewModel.updateTask()
            }
            onSaveClick()
        }
    )
}

/*
@Preview
@Composable
fun TaskEditScreenPreview() {
    TaskEditScreen(1)
}

 */