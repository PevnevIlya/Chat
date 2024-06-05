package com.example.myapplication.presentation.singlechat

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.presentation.composables.DefaultTextField
import com.example.myapplication.presentation.composables.LoadingScreen
import com.example.myapplication.presentation.navigation.Screens
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleChat(
    navController: NavController,
    companionEmail: String,
    email: String,
    viewModel: SingleChatViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val listSize = viewModel.messageList.size

    LaunchedEffect(listSize) {
        coroutineScope.launch {
            listState.animateScrollToItem(listSize)
        }
    }
    LaunchedEffect(companionEmail) {
        val userInfo = viewModel.getUserInfo(companionEmail)
        viewModel.companion = userInfo
    }
    viewModel.state.value.email = email
    viewModel.state.value.companionEmail = companionEmail
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.toastEvent.collectLatest { message ->
            Log.d("ChatTest", "message is $message")
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                Log.d("qwerty", "onstart")
                viewModel.startActivity(email, companionEmail)
            } else if (event == Lifecycle.Event.ON_STOP) {
                Log.d("qwerty", "onstop")
                viewModel.disconnect()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (viewModel.state.value.isLoading) {
        LoadingScreen()
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Color.LightGray)
            ) {
                AsyncImage(
                    model = android.R.drawable.ic_menu_call,
                    contentDescription = "Photo",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = viewModel.companion.name,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp),
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )

                IconButton(
                    onClick = { navController.navigate(Screens.MainScreen.route) },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Exit")
                }

            }
            // Chat
            Box(
                modifier = Modifier
                    .weight(0.9f)
                    .background(Color.Magenta)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = listState
                ) {
                    items(viewModel.messageList) { message ->
                        val isOwnMessage = message.email == email
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp),
                            contentAlignment = if (isOwnMessage) {
                                Alignment.CenterEnd
                            } else Alignment.CenterStart,
                            content = {
                                Column(
                                    modifier = if (isOwnMessage) {
                                        Modifier
                                            .background(Color.Blue)
                                            .padding(4.dp)
                                    } else {
                                        Modifier
                                            .background(Color.Red)
                                            .padding(4.dp)
                                    }
                                ) {
                                    Text(
                                        text = if (isOwnMessage) {
                                            "You"
                                        } else {
                                            viewModel.companion.name
                                        },
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Yellow
                                    )
                                    Text(
                                        text = message.text,
                                        color = Color.White
                                    )
                                    Text(
                                        text = message.formattedTime,
                                        color = Color.Black,
                                        modifier = Modifier
                                            .align(Alignment.End)
                                            .padding(top = 4.dp)
                                    )
                                }
                            }
                        )
                    }
                }
            }
            // Input
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f)
                    .background(Color.Green)
                    .padding(PaddingValues(all = 2.dp))
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                ) {
                    TextField(
                        value = viewModel.messageText.value,
                        onValueChange = { viewModel.onMessageTextChanged(it) },
                        modifier = Modifier
                            .weight(1f)
                    )
                    Button(
                        onClick = {

                            viewModel.sendMessage(
                                email = email,
                                chatId = viewModel.state.value.chatId
                            )
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .height(48.dp)

                    ) {
                        Text(text = "Отправить")
                    }
                    if (!viewModel.isEncrypted.value) {
                        Button(
                            onClick = {
                                viewModel.shiftMessageText()
                                viewModel.isEncrypted.value = !viewModel.isEncrypted.value
                            },
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .height(48.dp)

                        ) {
                            Text(text = "Зашифровать")
                        }
                    } else {
                        Button(
                            onClick = {
                                viewModel.restoreMessageText()
                                viewModel.isEncrypted.value = !viewModel.isEncrypted.value
                            },
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .height(48.dp)

                        ) {
                            Text(text = "Расшифровать")
                        }
                    }
                }
            }
        }
    }
}