package com.jks.recipe.di

import com.jks.recipe.repository.CommentRepository
import com.jks.recipe.repository.DefaultCommentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object CommentModule {


    @Provides
    @ActivityScoped
    fun provideCommentRepository() = DefaultCommentRepository() as CommentRepository
}