package com.example.composeroomsample.ui.theme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeroomsample.database.TaskDatabase
import com.example.composeroomsample.database.repository.TasksRepository
import com.example.composeroomsample.viewmodel.TaskDetailViewModel
import com.example.composeroomsample.viewmodel.TaskDetails
import kotlinx.coroutines.launch

@Composable
fun TaskDetailScreen(
    taskId: Int,
    navigateBack: () -> Unit,
    navigateToTaskEdit: (Int) -> Unit
) {
    val repository = TasksRepository(TaskDatabase.getDatabase(LocalContext.current).taskDao())
    val viewModel: TaskDetailViewModel = viewModel {
        TaskDetailViewModel(repository, taskId)
    }
    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "タスク詳細"
        )
        TaskDetailsBody(
            taskId = taskId,
            taskDetails = uiState.value.taskDetails,
            viewModel = viewModel,
            navigateBack = navigateBack,
            navigateToTaskEdit = navigateToTaskEdit
        )
    }
}

@Composable
private fun TaskDetailsBody(
    taskId: Int,
    taskDetails: TaskDetails,
    modifier : Modifier = Modifier,
    viewModel: TaskDetailViewModel,
    navigateBack: () -> Unit,
    navigateToTaskEdit: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaskDetailsColumn(
            taskTitle = taskDetails.title,
            taskDetails = taskDetails.detail,
        )
        Row (
            modifier = modifier.
                padding(top = 30.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Button(onClick = {
                navigateToTaskEdit(taskId)
                }
            ) {
                Text(
                    text = "編集",
                )
            }
            Button(onClick = {
                coroutineScope.launch {
                    viewModel.deleteTask()
                }
                    navigateBack()
                }
            ) {
                Text(
                    text = "消去"
                )
            }
        }
    }
}

@Composable
private fun TaskDetailsColumn(
    taskTitle: String,
    taskDetails: String,
    modifier: Modifier = Modifier
) {
   Column(
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.spacedBy(10.dp),
       modifier = modifier
           .fillMaxWidth()
           .padding(all = 10.dp)
   ) {
       Text(
           text = taskTitle,
           fontSize = 25.sp
       )
       Text(
           text = taskDetails,
           fontSize = 20.sp
       )
   }
}

@Preview
@Composable
fun TaskDetailColumnPreview(modifier: Modifier = Modifier) {
    TaskDetailsColumn(
        taskTitle = "Sample Task Title",
        taskDetails = "Sample Task Detail",
        modifier = modifier
            .fillMaxSize()
    )
}
