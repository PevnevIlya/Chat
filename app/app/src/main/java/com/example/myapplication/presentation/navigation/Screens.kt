package com.example.myapplication.presentation.navigation

sealed class Screens(val route: String){
    object MainScreen: Screens("main_screen")
    object RegistrationScreen: Screens("registration_screen")
    object LoginScreen: Screens("login_screen")
    object ChangeInfoScreen: Screens("change_info_screen")
    object ChatsScreen: Screens("chats_screen")
    object ProfileScreen: Screens("profile_screen")
    object SettingsScreen: Screens("settings_screen")
    object AddCompanionScreen: Screens("add_companion_screen")
    object SingleChatScreen: Screens("single_chat_screen")
}
