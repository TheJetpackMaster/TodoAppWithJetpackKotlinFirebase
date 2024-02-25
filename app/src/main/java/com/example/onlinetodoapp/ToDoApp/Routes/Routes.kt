package com.example.onlinetodoapp.ToDoApp.Routes

sealed class Routes(val route:String){
    object splash:Routes("splash")

    object AUTH:Routes("AUTH")
    object Signup:Routes("signup")
    object Login:Routes("login")

    object MAIN:Routes("MAIN")
    object EditTask:Routes("editTask")
    object AddTask:Routes("addTask")
}