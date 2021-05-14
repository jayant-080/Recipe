package com.jks.recipe.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jayant.xpost.other.safeCall
import com.jks.recipe.models.Cuisin
import com.jks.recipe.models.Recipie
import com.jks.recipe.others.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class DefaultAddRecToFavRepository: FavouriteRecipeRepository {
    val firestore = FirebaseFirestore.getInstance().collection("favrecipe")

    override suspend fun addRecToFav(
        title: String,
        category: String,
        instructions: String,
        imageUrl: String,
        date: Long,
        time:String,
        uid:String,
        recipeUid:String,
        notes:String

    ) = withContext(Dispatchers.IO){
        safeCall {
            val recipe = Recipie(uid = uid,title = title,category = category,instruction = instructions,date = date,imageUrl = imageUrl,timerequire = time,recipeUid = recipeUid,notes = notes)
            firestore.document(FirebaseAuth.getInstance().uid!!).collection("myfavrecipie").document(UUID.randomUUID().toString()).set(recipe).await()
            Resource.Success(Any())
        }
    }

    override suspend fun getAllFavRec()= withContext(Dispatchers.IO){
        safeCall {

            val recipeList = ArrayList<Recipie>()
            val getrecipe = firestore.document(FirebaseAuth.getInstance().uid!!).collection("myfavrecipie").get().await()
            for (documents in getrecipe.documents) {
                val category = documents.get("category").toString()
                val date = documents.get("date").toString().toLong()
                val uid = documents.get("uid").toString()
                val imageUrl = documents.get("imageUrl").toString()
                val title = documents.get("title").toString()
                val timerequire = documents.get("timerequire").toString()
                val instruction = documents.get("instruction").toString()
                val recipeUid=documents.get("recipeUid").toString()
                val notes=documents.get("notes").toString()
                val recipeNewList =
                    Recipie(uid = uid, date = date, imageUrl = imageUrl, category = category,title =title,timerequire = timerequire,instruction = instruction ,recipeUid = recipeUid,notes = notes)
                recipeList.add(recipeNewList)
            }
            Resource.Success(recipeList)
        }
    }
}