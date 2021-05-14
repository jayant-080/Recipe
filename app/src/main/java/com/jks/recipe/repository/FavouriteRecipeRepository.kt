package com.jks.recipe.repository

import com.jks.recipe.models.Recipie
import com.jks.recipe.others.Resource

interface FavouriteRecipeRepository {

    suspend fun addRecToFav(title:String,category:String,instructions:String,imageUrl:String,date:Long,time:String,uid:String,recipeUid:String,notes:String):Resource<Any>
    suspend fun getAllFavRec():Resource<ArrayList<Recipie>>


}