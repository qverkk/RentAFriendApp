package com.qverkk.touristrentafriend.ui.dashboard.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qverkk.touristrentafriend.data.User

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is a list of users"
    }
    val text: LiveData<String> = _text

    private val _users = MutableLiveData<MutableList<User>>().apply {
        value = mutableListOf()
    }
    val users: LiveData<MutableList<User>> = _users

    fun addUsers(userList: List<User>) {
        _users.value?.clear()
        _users.value?.addAll(userList)
    }

    fun addUser(user: User) {
        _users.value?.add(user)
    }
}