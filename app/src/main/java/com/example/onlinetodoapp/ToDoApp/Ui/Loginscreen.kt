package com.example.onlinetodoapp.ToDoApp.Ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onlinetodoapp.R
import com.example.onlinetodoapp.ToDoApp.Routes.Routes
import com.example.onlinetodoapp.ui.theme.Bg
import com.example.onlinetodoapp.ui.theme.PurpleDark
import com.google.firebase.auth.FirebaseAuth
import okhttp3.Route

@Composable
fun LoginScreen(navController: NavController) {


    val context = LocalContext.current

    val isLoading = remember {
        mutableStateOf<Boolean>(false)
    }

    var isError by remember {
        mutableStateOf(false)
    }

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    if (email.isNotEmpty() ||
        password.isNotEmpty()

    ) {
        isError = false
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
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Welcome Back",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.tasky),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(48.dp))

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
                        text = "Enter your password",
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

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Forgot password?",
                color = PurpleDark,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    if(email.isNotEmpty() || password.isNotEmpty()) {
                        performLogin(email, password, navController, context, isLoading)
                        isError = false
                    } else {
                        isError = true
                        Toast.makeText(
                            context,
                            "One of the field is empty",
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
            ) {
                if(isLoading.value){
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                else{
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}


private fun performLogin(
    email: String,
    password: String,
    navController: NavController,
    context: Context,
    isLoading: MutableState<Boolean>
) {
    isLoading.value = true
    val auth = FirebaseAuth.getInstance()
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            isLoading.value = false
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user != null && user.isEmailVerified) {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()

                    navController.popBackStack(Routes.AUTH.route, inclusive = true)
                    navController.navigate(BottomNavScreen.All.route){
                        popUpTo(BottomNavScreen.All.route)
                    }

                } else {
                    Toast.makeText(
                        context,
                        "Please verify your email before logging in",
                        Toast.LENGTH_SHORT
                    ).show()

                    sendVerification(
                        isLoading,
                        context,
                    )
                }
            } else {
                isLoading.value = false
                Toast.makeText(context, "Wrong credentials", Toast.LENGTH_SHORT).show()
            }
        }
}

private fun sendVerification(
    isLoading: MutableState<Boolean>,
    context: Context
) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    user?.sendEmailVerification()?.addOnCompleteListener { verificationTask ->
        isLoading.value = false
        if (verificationTask.isSuccessful) {
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
}