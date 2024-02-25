package com.example.onlinetodoapp.ToDoApp.Ui

import android.app.Activity
import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.onlinetodoapp.R
import com.example.onlinetodoapp.ui.theme.GrayDarkest
import com.example.onlinetodoapp.ui.theme.PurpleDark

@Composable
fun CustomBottomNav(navController: NavController,context: Context) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    DisposableEffect(backDispatcher) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentDestination = navBackStackEntry?.destination?.route

                if (currentDestination == BottomNavScreen.All.route) {
                    // If on the "All" screen, close the app
                    (context as? Activity)?.finish()
                } else {
                    // Otherwise, let the default back navigation happen
                    navController.popBackStack()
                }
            }
        }

        backDispatcher?.addCallback(callback)

        // Remove the callback if the effect is disposed
        onDispose {
            callback.remove()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                ambientColor = Color.Black,
                spotColor = Color.Black
            )
            .height(74.dp)
            .background(Color.White),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp)
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier
                    .size(80.dp)
                    .clickable(
                        onClick = {
                            navController.navigate(BottomNavScreen.All.route){
                                popUpTo(BottomNavScreen.All.route)
                            }
                        }
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = BottomNavScreen.All.icon),
                    contentDescription = null,
                    tint = if (currentDestination == BottomNavScreen.All.route) PurpleDark else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "All",
                    color = if(currentDestination == BottomNavScreen.All.route) PurpleDark else  GrayDarkest,
                    fontSize = 11.sp)
            }
            
            Column(
                modifier = Modifier
                    .size(80.dp)
                    .clickable(
                        onClick = {
                            navController.navigate(BottomNavScreen.Completed.route){
                                popUpTo(BottomNavScreen.All.route)
                            }
                        }
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = BottomNavScreen.Completed.icon),
                    contentDescription = null,
                    tint = if (currentDestination == BottomNavScreen.Completed.route) PurpleDark else Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Completed",
                    color = if(currentDestination == BottomNavScreen.Completed.route) PurpleDark else  GrayDarkest,
                    fontSize = 11.sp)

            }

            Column(
                modifier = Modifier
                    .size(80.dp)
                    .clickable(
                        onClick = {
                            navController.navigate(BottomNavScreen.Profile.route){
                                popUpTo(BottomNavScreen.All.route)
                            }
                        }
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = BottomNavScreen.Profile.icon),
                    contentDescription = null,
                    tint = if (currentDestination == BottomNavScreen.Profile.route) PurpleDark else Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Profile",
                    color = if(currentDestination == BottomNavScreen.Profile.route) PurpleDark else  GrayDarkest,
                    fontSize = 11.sp)

            }
        }
    }
}


sealed class BottomNavScreen(val route: String, val icon: Int) {
    object Completed : BottomNavScreen("All", R.drawable.baseline_check_24)
    object All : BottomNavScreen("Completed", R.drawable.baseline_format_list_bulleted_24)
    object Profile:BottomNavScreen("Profile",R.drawable.profile)
}
