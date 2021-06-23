package com.summit.offert.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.summit.core.network.repository.AdRepository

class CreateAddViewModel(
    private val adRepo: AdRepository
) : ViewModel() {


    private val _stateRegister = MutableLiveData<CreateAddViewState>()
    val stateRegister: LiveData<CreateAddViewState> get() = _stateRegister
}