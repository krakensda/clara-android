package com.pens.it.d4b2018.clara_android.mvvm.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pens.it.d4b2018.clara_android.mvvm.data.models.User
import com.pens.it.d4b2018.clara_android.mvvm.data.models.UserResponse
import com.pens.it.d4b2018.clara_android.mvvm.data.remote.Resource
import com.pens.it.d4b2018.clara_android.mvvm.data.repositories.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(
        private val repository: UserRepository
) : ViewModel() {

    private val _user: MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    val user: LiveData<Resource<UserResponse>>
        get() = _user

    fun getUser() = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser()
    }

}