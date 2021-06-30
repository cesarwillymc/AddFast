package com.summit.profile

import androidx.lifecycle.ViewModel
import com.summit.core.network.repository.UserRepository

class ProfileViewModel(private val userRepo:UserRepository): ViewModel() {

    val userInformation = userRepo.getUserTimeReal()

    fun clearUserInformation(){
        userRepo.deleteUser()
    }
}