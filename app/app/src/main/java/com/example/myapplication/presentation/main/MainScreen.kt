package com.example.myapplication.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.height
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.presentation.fragments.ChatsScreen
import com.example.myapplication.presentation.fragments.ProfileScreen
import com.example.myapplication.presentation.fragments.SettingsScreen
import com.example.myapplication.presentation.navigation.Screens

data class BottomNavigationItem(
    val title: String,
    val image: ImageVector,
    val unselectedImage: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val items = listOf(
        BottomNavigationItem(
            title = "Chats",
            image = Icons.Filled.Email,
            unselectedImage = Icons.Outlined.Email,
            hasNews = false,
            badgeCount = 45
        ),
        BottomNavigationItem(
            title = "Profile",
            image = Icons.Filled.Person,
            unselectedImage = Icons.Outlined.Person,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Settings",
            image = Icons.Filled.Settings,
            unselectedImage = Icons.Outlined.Settings,
            hasNews = false
        )
    )
    if(viewModel.goToChangeInfoActivity){
        navController.navigate(Screens.ChangeInfoScreen.route)
    }
    Scaffold (
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = viewModel.selectedItemIndex == index,
                        onClick = { viewModel.selectedItemIndex = index},
                        label = { Text(text = item.title) },
                        icon = {
                            BadgedBox(badge = {
                                if(item.badgeCount != null){
                                    Badge {
                                        Text(text = item.badgeCount.toString())
                                    }
                                } else if(item.hasNews){
                                    Badge {

                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = if(index == viewModel.selectedItemIndex) item.image else item.unselectedImage,
                                    contentDescription = item.title)
                            }
                        }
                    )
                }
            }
        }
        ) {
        if(viewModel.selectedItemIndex == 0) ChatsScreen()
        if(viewModel.selectedItemIndex == 1) ProfileScreen(viewModel)
        if(viewModel.selectedItemIndex == 2) SettingsScreen()
    }
}
