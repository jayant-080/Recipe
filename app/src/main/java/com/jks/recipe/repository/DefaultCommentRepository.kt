package com.jks.recipe.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.jayant.xpost.other.safeCall

import com.jks.recipe.models.comment
import com.jks.recipe.others.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

import java.util.*
import kotlin.collections.ArrayList


class DefaultCommentRepository : CommentRepository {

    val firestore = FirebaseFirestore.getInstance().collection("comments")
    override suspend fun addComments(messg: String, recipeUid: String, name: String)= withContext(Dispatchers.IO){
        safeCall {
            val comment =comment(recipeUid,name,messg)
            val randomuid= UUID.randomUUID().toString()
            firestore.document(recipeUid).collection("allcomments").document(randomuid).set(comment).await()
            Resource.Success(Any())
        }
    }

    override suspend fun getAllComment(recipeUid: String)= withContext(Dispatchers.IO){
        safeCall {

            val commentList = ArrayList<comment>()
            val getcomment = firestore.document(recipeUid).collection("allcomments").get().await()
            for (documents in getcomment.documents) {
                val name = documents.get("name").toString()
                val message = documents.get("message").toString()

                val commentNewList = comment(name = name,message = message)
                commentList.add(commentNewList)
            }
            Resource.Success(commentList)


        }
    }
}