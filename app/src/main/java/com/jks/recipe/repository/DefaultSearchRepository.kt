package com.jks.recipe.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.jayant.xpost.other.safeCall
import com.jks.recipe.models.Cuisin
import com.jks.recipe.others.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class DefaultSearchRepository : SearchRepository {

    val firestore = FirebaseFirestore.getInstance().collection("allrecipe")

    override suspend fun searchCuisin(query: String)= withContext(Dispatchers.IO){

        safeCall {


            val cuisinList = ArrayList<Cuisin>()
            val getcuisin = firestore.whereEqualTo("category",query).get().await()
            for (documents in getcuisin.documents) {
                val category = documents.get("category").toString()
                val date = documents.get("date").toString().toLong()
                val uid = documents.get("uid").toString()
                val imageUrl = documents.get("imageUrl").toString()
                val title = documents.get("title").toString()
                val timerequire = documents.get("timerequire").toString()
                val instruction = documents.get("instruction").toString()
                val recipeUid=documents.get("recipeUid").toString()
                val notes=documents.get("notes").toString()
                val cuisinNewList =
                    Cuisin(uid = uid, date = date, imageUrl = imageUrl, name = category,title =title,timerequire = timerequire,instruction = instruction ,recipieUid = recipeUid)
                cuisinList.add(cuisinNewList)
            }
            Resource.Success(cuisinList)
        }

    }
}