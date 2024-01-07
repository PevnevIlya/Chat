package com.example.myapplication.di

import android.content.Context
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.Room
import com.example.myapplication.data.implementations.ChangeInfoServiceImplementation
import com.example.myapplication.data.implementations.LoginServiceImplementation
import com.example.myapplication.data.implementations.RegistrationServiceImplementation
import com.example.myapplication.data.room.CompanionDao
import com.example.myapplication.data.room.CompanionDatabase
import com.example.myapplication.data.room.UserDao
import com.example.myapplication.data.room.UserDatabase
import com.example.myapplication.domain.usecases.validate.email.ValidateEmail
import com.example.myapplication.domain.usecases.validate.password.ValidatePassword
import com.example.myapplication.presentation.registration.RegistrationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRegistrationService(client: HttpClient): RegistrationServiceImplementation {
        return RegistrationServiceImplementation(client)
    }

    @Provides
    @Singleton
    fun provideLoginService(client: HttpClient): LoginServiceImplementation {
        return LoginServiceImplementation(client)
    }

    @Provides
    @Singleton
    fun provideChangeInfoService(@ApplicationContext appContext: Context, client: HttpClient): ChangeInfoServiceImplementation {
        return ChangeInfoServiceImplementation(appContext, client)
    }

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext appContext: Context): UserDatabase {
        return Room
            .databaseBuilder(appContext, UserDatabase::class.java, "User")
            .build()
    }

    @Singleton
    @Provides
    fun provideCompanionDatabase(@ApplicationContext appContext: Context): CompanionDatabase {
        return Room
            .databaseBuilder(appContext, CompanionDatabase::class.java, "Companion")
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.dao
    }

    @Singleton
    @Provides
    fun provideCompanionDao(companionDatabase: CompanionDatabase): CompanionDao {
        return companionDatabase.dao
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO){
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging)
            install(WebSockets)
        }
    }

    @Provides
    @Singleton
    fun provideValidateEmail(): ValidateEmail {
        return ValidateEmail()
    }

    @Provides
    @Singleton
    fun provideValidatePassword(): ValidatePassword {
        return ValidatePassword()
    }
}