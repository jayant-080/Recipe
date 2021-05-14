package com.jks.recipe.repository

import com.jks.recipe.models.Cuisin
import com.jks.recipe.others.Resource

interface CuisineRepository {
    suspend fun getAllCuisin():Resource<ArrayList<Cuisin>>
}