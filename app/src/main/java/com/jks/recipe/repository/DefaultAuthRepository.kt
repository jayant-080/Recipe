package com.jks.recipe.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jayant.xpost.other.safeCall
import com.jks.recipe.models.User
import com.jks.recipe.others.Resource
import kotlinx.coroutines.tasks.await

class DefaultAuthRepository : AuthRepository{

    val firestore = FirebaseFirestore.getInstance().collection("users")

    val auth = FirebaseAuth.getInstance()
    override suspend fun loginUser(
        email: String,
        password: String
    )= safeCall {
        val result = auth.signInWithEmailAndPassword(email,password).await()
        Resource.Success(result)

    }

    override suspend fun registerUser(
        userName: String,
        email: String,
        password: String,
        date:Long
    ) = safeCall {

        val result=auth.createUserWithEmailAndPassword(email,password).await()
        val uid = result.user?.uid!!
        val user = User(userName = userName,uid =uid,date = date )
        firestore.document(uid).set(user).await()
        Resource.Success(result)
    }


}