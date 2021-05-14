package com.jks.recipe.di

import com.jks.recipe.repository.DefaultFavCuisinRepository
import com.jks.recipe.repository.FavouriteCusinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object FavCuisinModule {

    @Provides
    @ActivityScoped
    fun provideFavCuisinRepository()= DefaultFavCuisinRepository() as FavouriteCusinRepository
}