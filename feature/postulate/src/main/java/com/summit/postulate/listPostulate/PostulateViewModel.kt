package com.summit.postulate.listPostulate

import androidx.lifecycle.ViewModel
import com.summit.core.network.repository.UserRepository

class PostulateViewModel(
    private val userRepo: UserRepository
):ViewModel() {
    val userInformation = userRepo.getUserTimeReal()
}