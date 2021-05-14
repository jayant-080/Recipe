package com.jks.recipe.models

data class User(
    val userName:String?=null,
    val uid:String?=null,
    val date:Long=System.currentTimeMillis()
){

}