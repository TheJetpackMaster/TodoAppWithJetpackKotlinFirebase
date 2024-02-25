package com.example.onlinetodoapp.ToDoApp.Ui.BottomScreens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.onlinetodoapp.ToDoApp.Routes.Routes
import com.example.onlinetodoapp.ToDoApp.Ui.BottomNavScreen
import com.example.onlinetodoapp.ui.theme.GrayDarkest
import com.example.onlinetodoapp.ui.theme.PurpleDark
import com.example.onlinetodoapp.ui.theme.PurpleLight
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavController, context: Context) {

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser


    var showLogoutDialog by remember {
        mutableStateOf(false)
    }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PurpleDark
                ),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Profile",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() })
                    {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PurpleLight)
                .padding(it)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (user != null && user.photoUrl != null && user.photoUrl.toString().isNotEmpty()) {
                    Log.d("Image uri", "${user.photoUrl}")
                    val decodedUri = Uri.decode(user.photoUrl.toString())
                    val imageUri = Uri.parse(decodedUri)

                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "Username", color = GrayDarkest, fontSize = 12.sp)

                    Spacer(modifier = Modifier.height(0.dp))

                    if (user != null) {
                        Text(
                            text = user.displayName!!,
                            color = Color.Black, fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    } else {
                        Text(text = "No user Found")
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PurpleDark
                    ),
                    onClick = {
                        showLogoutDialog = !showLogoutDialog
                    })
                {
                    Text(text = "Logout", color = Color.White)
                }
            }
            if (showLogoutDialog) {
                Dialog(onDismissRequest = {
                    showLogoutDialog = false
                })
                {
                    Column(
                        modifier = Modifier
                            .height(90.dp)
                            .width(300.dp)
                            .clip(RoundedCornerShape(32.dp))

                            .background(Color.White)
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = { showLogoutDialog = false })
                            {
                                Text(text = "Cancel")
                            }
                            Spacer(modifier = Modifier.width(16.dp))

                            Button(onClick = {
                                scope.launch { performLogout(navController, context) }

                                showLogoutDialog = !showLogoutDialog
                            }) {
                                Text(text = "Logout")
                            }
                        }
                    }
                }
            }
            if (showDeleteDialog) {
                Dialog(onDismissRequest = {
                    showDeleteDialog = false
                })
                {
                    Column(
                        modifier = Modifier
                            .height(90.dp)
                            .width(300.dp)
                            .clip(RoundedCornerShape(32.dp))

                            .background(Color.White)
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = { showDeleteDialog = false })
                            {
                                Text(text = "Cancel")
                            }
                            Spacer(modifier = Modifier.width(16.dp))

                            Button(onClick = {
                                user?.let {
                                    scope.launch {
                                        deleteAccount(user, navController, context)
                                    }

                                    showDeleteDialog = !showDeleteDialog
                                }

                            }) {
                                Text(text = "Delete")
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                   showDeleteDialog = !showDeleteDialog
                })
                {
                    Text(text = "Delete Account")
                }
            }

            Spacer(modifier = Modifier.height(96.dp))

        }
    }
}

private suspend fun performLogout(navController: NavController, context: Context) {
    if (isNetworkAvailable(context)) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            performFirebaseLogout(auth, navController, context)
        } else {
            Toast.makeText(context, "No user is currently signed in", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
    }
}

private suspend fun performFirebaseLogout(
    auth: FirebaseAuth,
    navController: NavController,
    context: Context
) {
    val isNetworkAvailable = isNetworkAvailable(context)
    val isFirebaseReachable = isFirebaseServerReachable()

    if (!isNetworkAvailable) {
        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()

    } else if (isNetworkAvailable && isFirebaseReachable) {
        auth.signOut()
        if (auth.currentUser == null) {
            // Clear the back stack up to the All screen
            navController.popBackStack(BottomNavScreen.All.route, inclusive = true)

            navController.navigate(Routes.AUTH.route) {
                // Clear the back stack of the AUTH graph
                popUpTo(Routes.AUTH.route) {
                    inclusive = true
                }
            }
        } else {
            // Handle the case where sign-out was not successful (e.g., show an error message)
            Toast.makeText(context, "Failed to log out", Toast.LENGTH_SHORT).show()
        }
    } else {
        delay(1000)
        Toast.makeText(context, "Poor connection Try Again", Toast.LENGTH_SHORT).show()
    }
}

private suspend fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkCapabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}

private suspend fun isFirebaseServerReachable(): Boolean {
    return try {
        withContext(Dispatchers.IO) {
            val url = URL("https://firebase.google.com/") // Replace with your Firebase server URL
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            urlConnection.connectTimeout = 1500 // Set timeout to 3 seconds
            urlConnection.connect()
            urlConnection.responseCode == 200
        }
    } catch (e: IOException) {
        false
    }
}


private suspend fun deleteAccount(
    user: FirebaseUser,
    navController: NavController,
    context: Context
) {
    val isNetworkAvailable = isNetworkAvailable(context)

    if (!isNetworkAvailable) {
        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()

    } else if (isNetworkAvailable && isFirebaseServerReachable()) {
        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Account deleted successfully", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate(Routes.Signup.route)
                } else {
                    Toast.makeText(context, "Failed to delete account", Toast.LENGTH_SHORT).show()
                }
            }
    } else {
        delay(1000)
        Toast.makeText(context, "Check internet connection! Try again", Toast.LENGTH_SHORT).show()
    }
}