package com.jks.recipe.di

import com.jks.recipe.repository.DefaultAddRecToFavRepository
import com.jks.recipe.repository.FavouriteRecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object FavRecipeModule {


    @Provides
    @ActivityScoped
    fun provideFavouriteRecipe() = DefaultAddRecToFavRepository() as FavouriteRecipeRepository
}