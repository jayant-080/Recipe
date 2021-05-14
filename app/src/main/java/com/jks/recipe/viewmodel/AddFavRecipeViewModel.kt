package com.jks.recipe.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayant.xpost.other.Event
import com.jks.recipe.models.Recipie
import com.jks.recipe.others.Resource
import com.jks.recipe.repository.FavouriteRecipeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddFavRecipeViewModel @ViewModelInject constructor(
    private val repository: FavouriteRecipeRepository,
    private val context: Context,
    private val dispatcher: CoroutineDispatcher=Dispatchers.Main
):ViewModel() {

    private val _addrectoFav = MutableLiveData<Event<Resource<Any>>>()
    val addrectoFav:LiveData<Event<Resource<Any>>> = _addrectoFav

    private val _getrecFav = MutableLiveData<Event<Resource<ArrayList<Recipie>>>>()
    val getrecFav:LiveData<Event<Resource<ArrayList<Recipie>>>> = _getrecFav


    fun addRecToFav(title:String,category:String,instructions:String,imageUrl:String,date:Long,time:String,uid:String,recipeUid:String,notes:String){

        _addrectoFav.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
           val result = repository.addRecToFav(uid = uid,title = title,category = category,instructions = instructions,time = time,imageUrl = imageUrl,date = date,recipeUid = recipeUid,notes = notes)
            _addrectoFav.postValue(Event(result))
        }
    }


    fun getAllFavRecipe(){
        _getrecFav.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = repository.getAllFavRec()
            _getrecFav.postValue(Event(result))
        }
    }




}