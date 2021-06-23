package com.summit.postulate.listPostulate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.summit.core.network.model.Postulacion
import com.summit.core.network.repository.PostulateRepository
import com.summit.core.network.repository.UserRepository
import kotlinx.coroutines.launch

class PostulateViewModel(
    private val userRepo: UserRepository,
    private val postulateRepo: PostulateRepository
) : ViewModel() {
    fun getUserInformation() = userRepo.getUserStatic()

    private val _dataListPostulates = MutableLiveData<List<Postulacion>>()
    val dataListPostulates: LiveData<List<Postulacion>> get() = _dataListPostulates

    private val _stateListPostulates = MutableLiveData<PostulateState>()
    val stateListPostulates: LiveData<PostulateState> get() = _stateListPostulates

    fun verMisPostulaciones(idUser: String) {
        viewModelScope.launch {
            _stateListPostulates.postValue(PostulateState.Loading)
            try {
                val resultado = postulateRepo.verMisPostulaciones(idUser)
                if (resultado.isNotEmpty()) {
                    _dataListPostulates.postValue(resultado)
                    _stateListPostulates.postValue(PostulateState.Complete)

                } else {
                    _stateListPostulates.postValue(PostulateState.Empty)
                }
            } catch (e: Exception) {
                _stateListPostulates.postValue(PostulateState.Error)
            }
        }

    }
}