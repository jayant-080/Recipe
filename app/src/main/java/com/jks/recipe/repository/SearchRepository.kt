package com.jks.recipe.repository

import com.jks.recipe.models.Cuisin
import com.jks.recipe.others.Resource

interface SearchRepository {

    suspend fun searchCuisin(query:String):Resource<ArrayList<Cuisin>>
}