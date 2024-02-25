package com.example.onlinetodoapp.ToDoApp.Data

data class Task(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    var completed: Boolean = false,
    val color:String = "white",
)