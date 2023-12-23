package com.example.composeroomsample.ui.theme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeroomsample.viewmodel.TaskDetails
import com.example.composeroomsample.viewmodel.TaskEntryViewModel
import com.example.composeroomsample.viewmodel.TaskUiState
import com.example.composeroomsample.database.TaskDatabase
import com.example.composeroomsample.database.repository.TasksRepository
import kotlinx.coroutines.launch

@Composable
fun TaskEntryScreen(
    navigateToHome: () -> Unit,
) {
    val repository = TasksRepository(TaskDatabase.getDatabase(LocalContext.current).taskDao())
    val viewModel: TaskEntryViewModel = viewModel {
        TaskEntryViewModel(repository)
    }
    val coroutineScope = rememberCoroutineScope()

    TaskEntryBody(
        taskUiState = viewModel.taskUiState,
        onTaskValueChange = viewModel::updateUiState,
        onSaveClick = {
            coroutineScope.launch {
            viewModel.saveItem()
            }
            navigateToHome()
        })
}

@Composable
fun TaskEntryBody(
    taskUiState: TaskUiState,
    onTaskValueChange: (TaskDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.padding(16.dp)
    ) {
        TaskInputForm(
            taskDetails = taskUiState.taskDetails,
            onValueChange = onTaskValueChange,
        )
        Button(
            onClick = onSaveClick,
            ) {
                Text(text = "保存")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInputForm(
    taskDetails: TaskDetails,
    modifier: Modifier = Modifier,
    onValueChange: (TaskDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = taskDetails.title,
            onValueChange = { onValueChange(taskDetails.copy(title = it)) },
            enabled = enabled,
        )
        OutlinedTextField(
            value = taskDetails.detail,
            onValueChange = { onValueChange(taskDetails.copy(detail = it)) },
            enabled = enabled,
        )
    }
}

/*
@Preview
@Composable
fun TaskEntryScreenPreview() {
    TaskEntryScreen(
    )
}
 */

@Preview
@Composable
fun TaskInputFormPreview() {
    val taskUiState = TaskUiState()
    val onTaskValueChange: (TaskDetails) -> Unit = { }
    TaskInputForm(
        taskDetails = taskUiState.taskDetails,
        onValueChange = onTaskValueChange,
        )
}