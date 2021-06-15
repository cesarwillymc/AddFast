package com.summit.postulate.listPostulate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.summit.core.network.model.Postulacion
import com.summit.core.network.repository.UserRepository

class PostulateViewModel(
    private val userRepo: UserRepository
):ViewModel() {
    val userInformation = userRepo.getUserTimeReal()

    private val _dataListPostulates = MutableLiveData<Postulacion>()
    val dataListPostulates:LiveData<Postulacion> get() =  _dataListPostulates
    private val _stateListPostulates= MutableLiveData<PostulateState>()

    val stateListPostulates : LiveData<PostulateState> get() = _stateListPostulates
}