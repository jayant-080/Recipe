package com.jks.recipe.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jayant.xpost.other.safeCall
import com.jks.recipe.models.Recipie
import com.jks.recipe.others.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class DefaultAddRecipiRepository : AddRecipieRepository {

    val firestoreAllrec = FirebaseFirestore.getInstance().collection("allrecipe")
    val firestoreCurrentUserRec = FirebaseFirestore.getInstance().collection("recipe")
    private val storage = FirebaseStorage.getInstance()
    override suspend fun addRecipie(
        imageUri: Uri,
        title: String,
        category: String,
        instruction: String,
        date: Long,
        timerequire:String,
        notes:String

    )= withContext(Dispatchers.IO) {
           safeCall {

               val uid = FirebaseAuth.getInstance().uid!!
               val imageresult =
                   storage.getReference(uid + date.toString()).putFile(imageUri).await()
               val downloadurl = imageresult.metadata?.reference?.downloadUrl?.await().toString()
              val recipeUid = UUID.randomUUID().toString()
               val recipe = Recipie(
                   uid = FirebaseAuth.getInstance().uid!!,
                   title = title,
                   category = category,
                   instruction = instruction,
                   date = date,
                   imageUrl = downloadurl,
                   timerequire = timerequire,
                   recipeUid = recipeUid,
                   notes = notes


               )

               firestoreCurrentUserRec.document(FirebaseAuth.getInstance().uid!!)
                       .collection("myrecipie").document(recipeUid).set(recipe)
                       .await()
               firestoreAllrec.document(recipeUid).set(recipe).await()
               Resource.Success(Any())

           }
    }
}