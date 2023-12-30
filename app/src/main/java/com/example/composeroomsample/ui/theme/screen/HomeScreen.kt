package com.example.composeroomsample.ui.theme.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeroomsample.database.Task
import com.example.composeroomsample.database.TaskDatabase
import com.example.composeroomsample.database.repository.TasksRepository
import com.example.composeroomsample.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToTaskEntry: () -> Unit,
    navigateToTaskDetail: (Int) -> Unit,
) {
    val repository = TasksRepository(TaskDatabase.getDatabase(LocalContext.current).taskDao())
    val viewModel: HomeViewModel = viewModel {
        HomeViewModel(repository)
    }
    val homeUiState by viewModel.homeUiState.collectAsState()

    Log.d("result", homeUiState.taskList.toString() + "タスクリスト")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTaskEntry,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            taskList = homeUiState.taskList,
            onItemClick =  navigateToTaskDetail,
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Composable
private fun HomeBody(
    taskList: List<Task>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (taskList.isEmpty()) {
            // todo
        } else {
            Text(
                text = "task list",
            )
            TaskList(
                taskList = taskList,
                onItemClick = { onItemClick(it.id) },
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun TaskList(
    taskList: List<Task>,
    onItemClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = taskList, key = { it.id }) { task ->
            TaskCard(
                task = task,
                modifier = Modifier
                    .clickable{ onItemClick(task)}
            )
        }
    }
}

@Composable
private fun TaskCard(
    task: Task, modifier: Modifier = Modifier
) {
   Card(
       elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
       modifier = modifier
           .fillMaxWidth()
           .padding(10.dp)
   ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = task.title,
                fontSize = 22.sp
            )
            Text(
                text = task.detail,
                fontSize = 20.sp
            )
        }
   }
}

@Preview
@Composable
fun TaskCard() {
    TaskCard(
        Task(1, "task_1", "test_test_test_test_test_test_test_test")
    )
}