package com.summit.home.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.summit.core.network.model.Anuncios
import com.summit.core.network.repository.AdRepository
import com.summit.core.network.repository.UserRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailAdViewModel(
    private val adRepo: AdRepository,
    userRepo: UserRepository
) : ViewModel() {

    val user = userRepo.getUserTimeReal()

    private val _state = MutableLiveData<DetailAdState>()
    val state: LiveData<DetailAdState> get() = _state

    private val _data = MutableLiveData<Anuncios>()
    val data: LiveData<Anuncios> get() = _data

    fun setAnuncioToView(anuncios: Anuncios) {
        _data.postValue(anuncios)
        _state.postValue(DetailAdState.Complete)
    }

    fun getAnuncioId(id: String) {
        viewModelScope.launch {
            delay(300)
            _state.postValue(DetailAdState.Loading)
            try {
                val response = adRepo.getAnuncioId(id)
                _data.postValue(response)
                _state.postValue(DetailAdState.Complete)
            } catch (e: Exception) {
                Log.e("getAnuncoId",e.message!!)
                _state.postValue(DetailAdState.Error)
            }
        }
    }

    fun addViewsAd(id: String) {
        viewModelScope.launch {
            try {
                adRepo.aumentarVisualizacionesAnuncios(id)
            } catch (e: Exception) {

            }
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}