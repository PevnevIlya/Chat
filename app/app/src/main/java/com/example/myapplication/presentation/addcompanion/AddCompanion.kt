package com.example.myapplication.presentation.addcompanion

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.myapplication.presentation.composables.DefaultButton
import com.example.myapplication.presentation.composables.DefaultTextField
import com.example.myapplication.presentation.composables.LoadingScreen
import com.example.myapplication.presentation.navigation.Screens
import com.example.myapplication.presentation.registration.RegistrationFormEvent

@Composable
fun AddCompanionScreen(
    navController: NavController,
    viewModel: AddCompanionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    if(viewModel.needToGoBack) {
        Toast.makeText(context, "User succesfully added!", Toast.LENGTH_LONG).show()
        navController.navigate(Screens.MainScreen.route)
    }
    if(viewModel.isAdded) {
        Toast.makeText(context, "User already added!", Toast.LENGTH_LONG).show()
        viewModel.isAdded = false
    }
    if(viewModel.isLoading) {
        LoadingScreen()
    } else {
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
                    value = viewModel.editValue,
                    onValueChange = {
                        viewModel.editValue = it
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                DefaultButton(
                    action = {
                        viewModel.onConfirmButtonClicked()
                    },
                    modifier = Modifier
                        .height(45.dp)
                        .width(200.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    buttonText = "Add",
                    textColor = Color.Black,
                    buttonColor = Color.White
                )
            }
        }
    }
}