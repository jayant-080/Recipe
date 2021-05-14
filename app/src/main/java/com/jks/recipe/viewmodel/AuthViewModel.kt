package com.jks.recipe.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.jayant.xpost.other.Event
import com.jks.recipe.R
import com.jks.recipe.others.Resource
import com.jks.recipe.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AuthViewModel @ViewModelInject constructor(

    private val repository: AuthRepository,
    private val context: Context,
    private val dispatcher:CoroutineDispatcher = Dispatchers.Main
) : ViewModel(){

    private val _registerStatus = MutableLiveData<Event<Resource<AuthResult>>>()
    val registerStatus :LiveData<Event<Resource<AuthResult>>> = _registerStatus

    private val _loginStatus = MutableLiveData<Event<Resource<AuthResult>>>()
    val loginStatus:LiveData<Event<Resource<AuthResult>>> = _loginStatus


    fun registerUser(userName:String,email:String,password:String,date:Long){
        val error = if (email.isEmpty() || password.isEmpty() || userName.isEmpty()) {
            context.getString(R.string.error_input_empty)
        }
        else if (userName.length < 4) {
            context.getString(R.string.error_username_too_short)
        } else if (userName.length > 15) {
            context.getString(R.string.error_username_too_long)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            context.getString(R.string.error_not_a_valid_email)
        } else null

        error?.let {
            _registerStatus.postValue(Event(Resource.Error(it)))
        }

        _registerStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = repository.registerUser(userName, email, password,date)
            _registerStatus.postValue(Event(result))
        }
    }


    fun loginUser(email:String,password:String){
        if(email.isEmpty() || password.isEmpty()){
            val error= context.getString(R.string.error_input_empty)
            _loginStatus.postValue(Event(Resource.Error(error)))
        }

        _loginStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch (dispatcher){
            val result= repository.loginUser(email,password)
            _loginStatus.postValue(Event(result))
        }
    }

}