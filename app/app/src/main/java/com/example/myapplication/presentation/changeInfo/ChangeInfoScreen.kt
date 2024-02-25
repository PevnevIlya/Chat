package com.example.myapplication.presentation.changeInfo

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.MyApp
import com.example.myapplication.R
import com.example.myapplication.data.room.User
import com.example.myapplication.presentation.composables.DefaultButton
import com.example.myapplication.presentation.composables.DefaultText
import com.example.myapplication.presentation.composables.DefaultTextField
import com.example.myapplication.presentation.main.MainViewModel
import com.example.myapplication.presentation.navigation.Screens
import io.ktor.utils.io.errors.IOException
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.sql.Blob
import kotlin.coroutines.coroutineContext

@Composable
fun ChangeInfoScreen(
    navController: NavController,
    viewModel: ChangeInfoViewModel = hiltViewModel()
){
    Log.d("Uri", " is a ${viewModel.user.photoUrl.toString()}")
    val selectedImageUri = remember {
        mutableStateOf<Uri?>(viewModel.user.photoUrl.toString().toUri())
    }
    Log.d("Uri", "selected image uri is a ${selectedImageUri.value}")
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> if(uri != null){
            selectedImageUri.value = uri
            Log.d("NewUri", uri.toString())
            viewModel.onValueChanged(ChangeInfoEvent.PhotoUrlChanged(uri.toString()))} }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(75.dp))
            AsyncImage(
                model = viewModel.loadedBitmap.value,
                contentDescription = "Photo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .clickable { singlePhotoPickerLauncher.launch("image/*")},
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(50.dp))
            DefaultTextField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Enter new name...",
                color = Color.Black,
                value = viewModel.user.name.toString(),
                onValueChange = { viewModel.onValueChanged(ChangeInfoEvent.NameChanged(it))})
            Spacer(modifier = Modifier.height(30.dp))
            DefaultTextField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Enter new status...",
                color = Color.Black,
                value = viewModel.user.status.toString(),
                onValueChange = { viewModel.onValueChanged(ChangeInfoEvent.StatusChanged(it))})
            Spacer(modifier = Modifier.height(30.dp))
            DefaultButton(
                action = {
                    viewModel.onValueChanged(ChangeInfoEvent.Submit)
                    navController.navigate(Screens.MainScreen.route) },
                modifier = Modifier
                    .height(45.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(20.dp)),
                buttonText = "Apply",
                textColor = Color.Black,
                buttonColor = Color.White
            )
        }
    }
}