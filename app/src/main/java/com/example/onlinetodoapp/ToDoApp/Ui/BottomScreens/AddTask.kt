package com.example.onlinetodoapp.ToDoApp.Ui.BottomScreens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.onlinetodoapp.ToDoApp.Data.TaskViewModel
import com.example.onlinetodoapp.ui.theme.BabyBlue
import com.example.onlinetodoapp.ui.theme.Bg
import com.example.onlinetodoapp.ui.theme.DefaultColor
import com.example.onlinetodoapp.ui.theme.GrayDarkest
import com.example.onlinetodoapp.ui.theme.LightBlue
import com.example.onlinetodoapp.ui.theme.PurpleDark
import com.example.onlinetodoapp.ui.theme.RedOrange
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(navController: NavController, taskViewModel: TaskViewModel,context: Context) {



    val focusRequester = FocusRequester()

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var color by remember {
        mutableStateOf("default")
    }

    var selected by remember {
        mutableStateOf("default")
    }

    Scaffold(
        containerColor = Bg,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .background(PurpleDark)
                            .padding(start = 16.dp, end = 16.dp),
                        text = "Add Task",
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
                .padding(paddingValues = it)
                .padding(18.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "CHOOSE A COLOR", color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(
                    modifier = Modifier
                        .size(50.dp)
                        .border(
                            3.dp,
                            if (selected == "default") Color.LightGray else Color.Transparent,
                            shape = CircleShape
                        ),
                    shape = CircleShape,
                    onClick = {
                        color = "default"
                        selected = "default"
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DefaultColor
                    )
                )
                {}

                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    modifier = Modifier
                        .size(50.dp)
                        .border(
                            3.dp,
                            color = if (selected == "babyBlue") Color.Gray else Color.Transparent,
                            shape = CircleShape
                        ),
                    shape = CircleShape,
                    onClick = {
                        color = "babyBlue"
                        selected = "babyBlue"
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BabyBlue
                    )
                )
                {}

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    modifier = Modifier
                        .size(50.dp)
                        .border(
                            3.dp,
                            color = if (selected == "lightBlue") Color.Gray else Color.Transparent,
                            shape = CircleShape
                        ),
                    shape = CircleShape,
                    onClick = {
                        color = "lightBlue"
                        selected = "lightBlue"
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightBlue
                    )
                )
                {}
                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    modifier = Modifier
                        .size(50.dp)
                        .border(
                            3.dp,
                            color = if (selected == "redOrange") Color.Gray else Color.Transparent,
                            shape = CircleShape
                        ),
                    shape = CircleShape,
                    onClick = {
                        color = "redOrange"
                        selected = "redOrange"
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RedOrange
                    )
                )
                {}

            }


            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = title,
                onValueChange = { title = it },
                textStyle = TextStyle.Default,
                placeholder = {
                    Text(text = "Title", color = GrayDarkest, fontSize = 15.sp)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color.Transparent)
                    .padding(start = 0.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        
                    }
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = description,
                onValueChange = { description = it },
                textStyle = TextStyle.Default,
                placeholder = {
                    Text(text = "Detail", color = GrayDarkest, fontSize = 15.sp)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color.Transparent)
                    .padding(start = 0.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {

                    }
                )

            )

            Spacer(modifier = Modifier.height(64.dp))


            Button(
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        taskViewModel.viewModelScope.launch {
                            taskViewModel.addTask(title, description, color = color, context)
                        }
                        navController.popBackStack()
                        Toast.makeText(
                            context,
                            "Task added successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "One of the field is empty..",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                },
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleDark
                )
            )
            {
                Text(
                    text = "Add",
                    color = Color.White,
                    fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}