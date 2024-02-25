package com.example.onlinetodoapp.ToDoApp.Ui.BottomScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onlinetodoapp.ToDoApp.CustomNoteDesign.Completed
import com.example.onlinetodoapp.ToDoApp.Data.TaskViewModel
import com.example.onlinetodoapp.ui.theme.BabyBlue
import com.example.onlinetodoapp.ui.theme.DefaultColor
import com.example.onlinetodoapp.ui.theme.LightBlue
import com.example.onlinetodoapp.ui.theme.PurpleDark
import com.example.onlinetodoapp.ui.theme.PurpleLight
import com.example.onlinetodoapp.ui.theme.RedOrange
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedTask(navController: NavController, taskViewModel: TaskViewModel) {


    val completedTasks by taskViewModel.completedTasks.collectAsState()
    val context = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser


    DisposableEffect(Unit) {
        if (currentUser != null) {
            val userId = currentUser.uid
            taskViewModel.fetchCompletedTasks(userId)
        }

        onDispose { /* Cleanup, if needed */ }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .background(PurpleDark)
                            .padding(start = 16.dp, end = 16.dp),
                        text = "Completed Task",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() })
                    {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PurpleDark
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(PurpleLight)
                .padding(top = 16.dp, bottom = 0.dp, start = 15.dp, end = 15.dp)
        ) {
            LazyColumn {
                items(completedTasks) { task ->

                    Completed(
                        title = task.title,
                        description = task.description,
                        bgColor =
                        if (task.color == "babyBlue") BabyBlue
                        else if (task.color == "lightBlue") LightBlue
                        else if (task.color == "redOrange") RedOrange
                        else DefaultColor
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            if (completedTasks.isEmpty()) {
                Text(text = "No completed tasks", fontSize = 16.sp, color = Color.Gray)
            }
        }
    }
}

