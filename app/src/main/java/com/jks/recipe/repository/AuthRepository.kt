package com.jks.recipe.repository

import com.google.firebase.auth.AuthResult
import com.jks.recipe.others.Resource

interface AuthRepository {

    suspend fun loginUser(email:String,password:String):Resource<AuthResult>
    suspend fun  registerUser(userName:String,email:String,password:String,date:Long):Resource<AuthResult>
}