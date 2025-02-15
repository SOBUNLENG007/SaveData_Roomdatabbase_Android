package com.example.roomdatabase_1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase_1.data.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository): ViewModel(){

    val viewList: StateFlow<List<User>> = repository.getAllUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun addUser(name : String){
        viewModelScope.launch {
            repository.insertUser(User(name = name))
        }
    }

    fun updateUser (user: User, newName : String){
        viewModelScope.launch {
            repository.updateUser(user.copy(name = newName))
        }
    }


    fun deleteUser(user: User){
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }



}