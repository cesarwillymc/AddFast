package com.summit.navhost

import androidx.lifecycle.ViewModel
import com.summit.core.network.repository.UserRepository

class NavHostViewModel(private val userRepo:UserRepository):ViewModel() {
    val getUserData = userRepo.getUserTimeReal()
    fun deleteUser()= userRepo.deleteUser()
    fun getStaticDataUser()= userRepo.getUserStatic()
}