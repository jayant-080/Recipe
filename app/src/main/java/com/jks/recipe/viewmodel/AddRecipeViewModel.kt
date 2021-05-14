package com.jks.recipe.viewmodel

import android.content.Context
import android.icu.text.CaseMap
import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayant.xpost.other.Event
import com.jks.recipe.R
import com.jks.recipe.others.Resource
import com.jks.recipe.repository.AddRecipieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddRecipeViewModel @ViewModelInject constructor(
    private val repository: AddRecipieRepository,
    private val context: Context,
    private val dispatcher: CoroutineDispatcher= Dispatchers.Main
): ViewModel() {

    private val _addrecStatus = MutableLiveData<Event<Resource<Any>>>()
    val addrecStatus:LiveData<Event<Resource<Any>>> = _addrecStatus


    private val _imageStatus = MutableLiveData<Uri>()
    val imageStatus:LiveData<Uri> = _imageStatus

    fun setImage(uri:Uri){
        _imageStatus.postValue(uri)
    }

    fun addRecipe(imageUri:Uri,title:String,category:String,instruction:String,date:Long,timerequire:String,notes:String){

        if (title.equals("") || category.equals("") || instruction.equals("") || notes.equals("")) {
           val err= context.getString(R.string.error_input_empty)
            _addrecStatus.postValue(Event(Resource.Error(err)))
        }
        _addrecStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
           val result = repository.addRecipie(imageUri,title,category,instruction,date,timerequire,notes)
          _addrecStatus.postValue(Event(result))
        }


    }


}