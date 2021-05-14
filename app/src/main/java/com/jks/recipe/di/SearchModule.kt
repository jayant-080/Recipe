package com.jks.recipe.di

import com.jks.recipe.repository.DefaultSearchRepository
import com.jks.recipe.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object SearchModule {

    @Provides
    @ActivityScoped
    fun provideSearchRepository() = DefaultSearchRepository() as SearchRepository
}