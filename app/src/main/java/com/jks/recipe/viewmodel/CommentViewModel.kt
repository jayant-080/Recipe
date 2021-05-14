package com.jks.recipe.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayant.xpost.other.Event
import com.jks.recipe.R
import com.jks.recipe.models.comment
import com.jks.recipe.others.Resource
import com.jks.recipe.repository.CommentRepository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentViewModel @ViewModelInject constructor(
    private val repository: CommentRepository,
    private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
):ViewModel() {

    private val _addCommentStatus= MutableLiveData<Event<Resource<Any>>>()
    val addCommentStatus:LiveData<Event<Resource<Any>>> = _addCommentStatus

    private val _getCommentStatus= MutableLiveData<Event<Resource<ArrayList<comment>>>>()
    val getCommentStatus:LiveData<Event<Resource<ArrayList<comment>>>> = _getCommentStatus

    fun addComment(recipeUid:String,name:String,message:String){
        if(message.isEmpty()){
            val err= context.getString(R.string.error_input_empty)
            _addCommentStatus.postValue(Event(Resource.Error(err)))
        }
        _addCommentStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch (dispatcher){
            val result= repository.addComments(message,recipeUid,name)
            _addCommentStatus.postValue(Event(result))
        }
    }

    fun getAllComments(recipeUid: String){
        _getCommentStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = repository.getAllComment(recipeUid)
            _getCommentStatus.postValue(Event(result))
        }

    }

}