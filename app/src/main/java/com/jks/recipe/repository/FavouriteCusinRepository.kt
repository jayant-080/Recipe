package com.jks.recipe.repository

import com.jks.recipe.models.Cuisin
import com.jks.recipe.others.Resource

interface FavouriteCusinRepository {


    suspend fun getAllFavCuisin():Resource<ArrayList<Cuisin>>

}