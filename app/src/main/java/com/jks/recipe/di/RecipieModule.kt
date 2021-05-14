package com.jks.recipe.di

import com.jks.recipe.repository.AddRecipieRepository
import com.jks.recipe.repository.DefaultAddRecipiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object RecipieModule {


    @Provides
    @ActivityScoped
    fun provideAddRecipeRepository()= DefaultAddRecipiRepository() as AddRecipieRepository
}