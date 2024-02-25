package com.example.onlinetodoapp.ToDoApp

import android.content.Context
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.onlinetodoapp.ToDoApp.Data.TaskViewModel
import com.example.onlinetodoapp.ToDoApp.Routes.Routes
import com.example.onlinetodoapp.ToDoApp.Ui.BottomNavScreen
import com.example.onlinetodoapp.ToDoApp.Ui.BottomScreens.AddTask
import com.example.onlinetodoapp.ToDoApp.Ui.BottomScreens.AllTask
import com.example.onlinetodoapp.ToDoApp.Ui.BottomScreens.CompletedTask
import com.example.onlinetodoapp.ToDoApp.Ui.BottomScreens.EditTask
import com.example.onlinetodoapp.ToDoApp.Ui.BottomScreens.Profile
import com.example.onlinetodoapp.ToDoApp.Ui.LoginScreen
import com.example.onlinetodoapp.ToDoApp.Ui.MainScreen
import com.example.onlinetodoapp.ToDoApp.Ui.SignupScreen
import com.example.onlinetodoapp.ToDoApp.Ui.SplashScreen


fun NavGraphBuilder.AuthNav(navController: NavHostController,context: Context) {
    navigation(startDestination = Routes.Signup.route, route = Routes.AUTH.route) {
        composable("signup") { SignupScreen(navController = navController,context) }
        composable("login") { LoginScreen(navController = navController) }
    }
}


@Composable
fun BottomNav(navController: NavHostController, taskViewModel: TaskViewModel, context: Context) {
    NavHost(navController = navController, startDestination = Routes.splash.route,
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300))
        } ) {

        composable(Routes.splash.route) { SplashScreen(navController = navController) }

        AuthNav(navController,context)

        composable(BottomNavScreen.All.route) {
            AllTask(
                navController = navController,
                taskViewModel,
                context
            )
        }
        composable(BottomNavScreen.Completed.route) {
            CompletedTask(
                navController = navController,
                taskViewModel
            )
        }
        composable(BottomNavScreen.Profile.route) {
            Profile(
                navController = navController,
                context
            )
        }

        composable(Routes.AddTask.route) { AddTask(navController = navController, taskViewModel,context) }

        composable(
            route = "${Routes.EditTask.route}/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->

            val taskId = backStackEntry.arguments?.getString("taskId")
            EditTask(
                navController = navController,
                taskId = taskId ?: "",
                taskViewModel = taskViewModel
            )
        }
    }
}

@Composable
fun RootNav(navController: NavHostController, taskViewModel: TaskViewModel, context: Context) {
    NavHost(navController = navController, startDestination = Routes.MAIN.route,
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300))
        }
        ) {

        composable(Routes.MAIN.route) {
            MainScreen(taskViewModel, context)
        }
    }
}