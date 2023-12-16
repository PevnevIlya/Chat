package com.example.myapplication.presentation.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.presentation.composables.DefaultButton
import com.example.myapplication.presentation.composables.DefaultText
import com.example.myapplication.presentation.composables.DefaultTextField
import com.example.myapplication.presentation.composables.LoadingScreen
import com.example.myapplication.presentation.navigation.Screens
import com.example.myapplication.presentation.registration.RegistrationFormEvent
import com.example.myapplication.presentation.registration.RegistrationViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is LoginViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Login successful",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(Screens.MainScreen.route)
                }
                is LoginViewModel.ValidationEvent.Fail -> {
                    Toast.makeText(
                        context,
                        "Login failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    if(!viewModel.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                DefaultTextField(
                    modifier = Modifier
                        .height(20.dp)
                        .width(150.dp),
                    text = "Enter email..",
                    color = Color.Gray,
                    value = viewModel.state.email,
                    onValueChange = {
                        viewModel.onEvent(LoginFormEvent.EmailChanged(it))
                    }
                )
                if (viewModel.state.emailError != null) {
                    DefaultText(
                        text = viewModel.state.emailError!!,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))

                DefaultTextField(
                    modifier = Modifier
                        .height(20.dp)
                        .width(150.dp),
                    text = "Enter password..",
                    color = Color.Gray,
                    value = viewModel.state.password,
                    onValueChange = {
                        viewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                    },
                    isError = viewModel.state.emailError != null
                )
                if (viewModel.state.passwordError != null) {
                    DefaultText(
                        text = viewModel.state.passwordError!!,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
                Spacer(modifier = Modifier.height(75.dp))

                DefaultButton(
                    action = {
                        viewModel.onEvent(LoginFormEvent.Submit)
                    },
                    modifier = Modifier
                        .height(45.dp)
                        .width(200.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    buttonText = "Login",
                    textColor = Color.Black,
                    buttonColor = Color.White
                )

                Spacer(modifier = Modifier.height(15.dp))

                DefaultButton(
                    action = {
                        navController.navigate(Screens.RegistrationScreen.route)
                    },
                    modifier = Modifier
                        .height(45.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    buttonText = "Back",
                    textColor = Color.Black,
                    buttonColor = Color.White
                )
            }
        }
    } else {
        LoadingScreen()
    }
}