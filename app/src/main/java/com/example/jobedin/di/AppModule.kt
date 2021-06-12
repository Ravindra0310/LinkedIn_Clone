package com.example.jobedin.di

import com.example.jobedin.repository.LinkedInRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesRepository(): LinkedInRepository {
        return LinkedInRepository()
    }

}