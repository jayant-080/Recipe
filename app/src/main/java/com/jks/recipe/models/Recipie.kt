package com.jks.recipe.models

import android.net.Uri

data class Recipie(
    val uid:String,
    val title:String?=null,
    val imageUri:Uri?=null,
    val category:String?=null,
    val date:Long= System.currentTimeMillis(),
    val imageUrl:String?=null,
    val instruction:String?=null,
    val timerequire:String?=null,
    val recipeUid:String?=null,
    val notes:String?=null

) {
}