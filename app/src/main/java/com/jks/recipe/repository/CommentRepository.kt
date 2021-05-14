package com.jks.recipe.repository

import com.jks.recipe.models.comment
import com.jks.recipe.others.Resource


interface CommentRepository {
    suspend fun addComments(messg:String,recipeUid:String,name:String):Resource<Any>
    suspend fun getAllComment(recipeUid: String):Resource<ArrayList<comment>>
}