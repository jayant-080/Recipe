package com.jayant.xpost.other

import com.jks.recipe.others.Resource
import java.lang.Exception

inline fun<T> safeCall( action: ()-> Resource<T>): Resource<T>{

    return try {
        action()
    }catch (e : Exception){
        Resource.Error(e.message?: "An unknown error occured")
    }

}