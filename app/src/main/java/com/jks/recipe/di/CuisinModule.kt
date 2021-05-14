package com.jks.recipe.di

import com.jks.recipe.repository.CuisineRepository
import com.jks.recipe.repository.DefaultCuisinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped


@Module
@InstallIn(ActivityComponent::class)
object CuisinModule {

    @Provides
    @ActivityScoped
    fun provideCuisinRepository() = DefaultCuisinRepository() as CuisineRepository
}