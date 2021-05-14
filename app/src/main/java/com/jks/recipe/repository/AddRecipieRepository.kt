package com.jks.recipe.repository

import android.net.Uri
import com.jks.recipe.others.Resource

interface AddRecipieRepository {

    suspend fun addRecipie(imageUri:Uri,title:String,category:String,instruction:String,date:Long,timerequire:String,notes:String):Resource<Any>
}