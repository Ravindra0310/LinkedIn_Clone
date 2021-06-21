package com.example.jobedin.di

import com.example.jobedin.data.remote.api.NotificationApi
import com.example.jobedin.repository.ChatRepository
import com.example.jobedin.repository.LinkedInRepository
import com.example.jobedin.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesRepository(notificationApi: NotificationApi): LinkedInRepository {
        return LinkedInRepository(notificationApi)

    }

    @Provides
    fun providesChatRepository(notificationApi: NotificationApi): ChatRepository {
        return ChatRepository(notificationApi)
    }

    @Singleton
    @Provides
    fun providesNotification(): NotificationApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NotificationApi::class.java)
    }

}