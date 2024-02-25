package com.example.onlinetodoapp.ToDoApp.Ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.onlinetodoapp.ToDoApp.Routes.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {


    LaunchedEffect(Unit) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null && currentUser.isEmailVerified) {
            delay(100) // Adjust the delay duration as needed (in milliseconds)
            navController.popBackStack()
            navController.navigate(BottomNavScreen.All.route)
        } else {
            delay(100) // Adjust the delay duration as needed (in milliseconds)
            navController.popBackStack()
            navController.navigate(Routes.AUTH.route)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

    }

}