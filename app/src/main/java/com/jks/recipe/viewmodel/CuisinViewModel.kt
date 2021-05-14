package com.jks.recipe.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayant.xpost.other.Event
import com.jks.recipe.models.Cuisin
import com.jks.recipe.others.Resource
import com.jks.recipe.repository.CuisineRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CuisinViewModel @ViewModelInject constructor(
    private val repository: CuisineRepository,
    private val context: Context,
    private val dispatcher: CoroutineDispatcher=Dispatchers.Main

):ViewModel() {

    private val _getallCuisinStatus= MutableLiveData<Event<Resource<ArrayList<Cuisin>>>>()
    val getallCuisinStatus:LiveData<Event<Resource<ArrayList<Cuisin>>>> = _getallCuisinStatus


    fun getAllCuisin(){

        _getallCuisinStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {

            val result= repository.getAllCuisin()
            _getallCuisinStatus.postValue(Event(result))
        }


    }


}