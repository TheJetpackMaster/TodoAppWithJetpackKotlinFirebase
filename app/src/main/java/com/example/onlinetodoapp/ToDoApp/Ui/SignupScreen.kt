@file:Suppress("DEPRECATION")

package com.example.onlinetodoapp.ToDoApp.Ui

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.onlinetodoapp.R
import com.example.onlinetodoapp.ToDoApp.Routes.Routes
import com.example.onlinetodoapp.ui.theme.Bg
import com.example.onlinetodoapp.ui.theme.PurpleDark
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

@Composable
fun SignupScreen(navController: NavController, context: Context) {


    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    DisposableEffect(backDispatcher) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                (context as? Activity)?.finish()
            }
        }

        backDispatcher?.addCallback(callback)

        // Remove the callback if the effect is disposed
        onDispose {
            callback.remove()
        }
    }


    var fullName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }

    var isError by remember {
        mutableStateOf(false)
    }

    var isPasswordError by remember {
        mutableStateOf(false)
    }

    if (email.isNotEmpty() ||
        password.isNotEmpty() ||
        confirmPassword.isNotEmpty() ||
        fullName.isNotEmpty()
    ) {
        isError = false
    }

    if (password == confirmPassword) {
        isPasswordError = false
    }

    val isLoading = remember {
        mutableStateOf<Boolean>(false)
    }


    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 36.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Welcome to Tasky,",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Let's help to meet up your\n" +
                        "tasks,", fontSize = 14.sp, color = Color.Black.copy(.8f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, PurpleDark, CircleShape)
                .background(Color.LightGray.copy(.3f))
                .clickable(
                    onClick = { launcher.launch("image/*") }
                ),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    // Use Coil to load and display the image
                    Image(
                        painter = rememberImagePainter(
                            data = imageUri,
                            builder = {
                                transformations(CircleCropTransformation())
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(text = "+", color = Color.Black, fontSize = 30.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                modifier = Modifier
                    .shadow(
                        5.dp,
                        ambientColor = Color.LightGray,
                        spotColor = Color.LightGray,
                        shape = CircleShape
                    )
                    .fillMaxWidth()
                    .height(54.dp),
                shape = CircleShape,
                placeholder = {
                    Text(
                        text = "Enter your full name",
                        fontSize = 14.sp, color = Color.Black
                    )
                },
                value = fullName,
                onValueChange = { fullName = it },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PurpleDark,
                    disabledTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    errorCursorColor = Color.Red
                ),
                isError = isError
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                modifier = Modifier
                    .shadow(
                        5.dp,
                        ambientColor = Color.LightGray,
                        spotColor = Color.LightGray,
                        shape = CircleShape
                    )
                    .fillMaxWidth()
                    .height(54.dp),
                shape = CircleShape,
                placeholder = {
                    Text(
                        text = "Enter your email",
                        fontSize = 14.sp, color = Color.Black
                    )
                },
                value = email,
                onValueChange = { email = it },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PurpleDark,
                    disabledTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    errorCursorColor = Color.Red
                ),
                isError = isError
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                modifier = Modifier
                    .shadow(
                        5.dp,
                        ambientColor = Color.LightGray,
                        spotColor = Color.LightGray,
                        shape = CircleShape
                    )
                    .fillMaxWidth()
                    .height(54.dp),
                shape = CircleShape,
                placeholder = {
                    Text(
                        text = "Enter password",
                        fontSize = 14.sp, color = Color.Black
                    )
                },
                value = password,
                onValueChange = { password = it },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PurpleDark,
                    disabledTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    errorCursorColor = Color.Red
                ),
                isError = isError
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                modifier = Modifier
                    .shadow(
                        5.dp,
                        ambientColor = Color.LightGray,
                        spotColor = Color.LightGray,
                        shape = CircleShape
                    )
                    .fillMaxWidth()
                    .height(54.dp),
                shape = CircleShape,
                placeholder = {
                    Text(
                        text = if (isPasswordError) "Password doesn't match" else "Confirm password",
                        fontSize = 14.sp, color = Color.Black
                    )
                },
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PurpleDark,
                    disabledTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                ),
                isError = isError
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        Toast.makeText(
                            context,
                            "One of the fields is empty !",
                            Toast.LENGTH_SHORT
                        ).show()
                        isError = true
                    } else if (password != confirmPassword) {
                        isPasswordError = true
                        Toast.makeText(
                            context,
                            "Password doesn't match!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        isLoading.value = true
                        performSignup(
                            imageUri,
                            fullName,
                            email,
                            password,
                            navController,
                            context,
                            isLoading
                        )
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
                if(isLoading.value){
                    CircularProgressIndicator()
                }
                else{
                    Text(
                        text = "Register",
                        color = Color.White,
                        fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                    )
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account?",
                    color = Color.Black,
                    fontSize = 15.sp
                )

                Text(
                    modifier = Modifier.clickable(
                        onClick = {
                            navController.navigate(Routes.Login.route) {
                                popUpTo(Routes.Login.route) {
                                    inclusive = true
                                }
                            }
                        }
                    ),
                    text = " Sign in",
                    color = PurpleDark,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

private fun performSignup(
    imageUri: Uri?,
    fullName: String,
    email: String,
    password: String,
    navController: NavController,
    context: Context,
    isLoading: MutableState<Boolean>
) {
    val auth = FirebaseAuth.getInstance()
    isLoading.value = true

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .setPhotoUri(imageUri)
                    .build()

                user?.updateProfile(profileUpdates)?.addOnCompleteListener { profileUpdateTask ->
                    if (profileUpdateTask.isSuccessful) {
                        user.sendEmailVerification().addOnCompleteListener { verificationTask ->
                            isLoading.value = false
                            if (verificationTask.isSuccessful) {

                                navController.navigate(Routes.Login.route)

                                Toast.makeText(
                                    context,
                                    "Please check your email for verification",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val errorMessage =
                                    verificationTask.exception?.message ?: "Unknown error"
                                Toast.makeText(
                                    context,
                                    "Failed to send verification email: $errorMessage",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        isLoading.value = false
                        val errorMessage = profileUpdateTask.exception?.message ?: "Unknown error"
                        Toast.makeText(
                            context,
                            "Failed to update profile: $errorMessage",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                isLoading.value = false
                val errorMessage = task.exception?.message ?: "Unknown error"
                Toast.makeText(
                    context,
                    "Failed to create account: $errorMessage",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}