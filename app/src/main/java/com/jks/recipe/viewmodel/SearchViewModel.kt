package com.jks.recipe.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayant.xpost.other.Event
import com.jks.recipe.R
import com.jks.recipe.models.Cuisin
import com.jks.recipe.others.Resource
import com.jks.recipe.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel @ViewModelInject constructor(
    private val repository: SearchRepository,
    private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
):ViewModel() {


    private val _searchStatus = MutableLiveData<Event<Resource<ArrayList<Cuisin>>>>()
    val searchStatus:LiveData<Event<Resource<ArrayList<Cuisin>>>> = _searchStatus

    fun searchCuisin(query:String){

        if(query.isEmpty()){
            val err = context.getString(R.string.error_input_empty)
            _searchStatus.postValue(Event(Resource.Error(err)))
        }
        _searchStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {

            val result = repository.searchCuisin(query)
            _searchStatus.postValue(Event(result))
        }

    }


}