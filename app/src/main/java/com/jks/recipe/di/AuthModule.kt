package com.jks.recipe.di

import com.jks.recipe.repository.AuthRepository
import com.jks.recipe.repository.DefaultAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object AuthModule {

    @Provides
    @ActivityScoped
    fun provideAuthRepository() = DefaultAuthRepository() as AuthRepository
}