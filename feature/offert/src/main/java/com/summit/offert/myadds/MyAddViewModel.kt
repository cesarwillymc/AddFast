package com.summit.offert.myadds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.summit.core.network.model.Anuncios
import com.summit.core.network.repository.AdRepository
import com.summit.core.network.repository.GpsRepository
import com.summit.core.network.repository.UserRepository
import kotlinx.coroutines.launch

class MyAddViewModel(
    private val userRepo: UserRepository,
    private val adRepo: AdRepository,
    repoUbi: GpsRepository,
):ViewModel() {
    val getUbicacion = repoUbi.getUbicacion()

    fun getUserInfo()=userRepo.getUserStatic()

    private val _dataListAdds = MutableLiveData<List<Anuncios>>()
    val dataListAdds: LiveData<List<Anuncios>> get() = _dataListAdds

    private val _stateListAdds = MutableLiveData<MyAddViewState>()
    val stateListAdds: LiveData<MyAddViewState> get() = _stateListAdds

    fun verMisPostulaciones(id: String) {
        viewModelScope.launch {
            _stateListAdds.postValue(MyAddViewState.Loading)
            try {
                val resultado = adRepo.getMisAnuncios(id)
                if (resultado.isNotEmpty()) {
                    _dataListAdds.postValue(resultado)
                    _stateListAdds.postValue(MyAddViewState.Complete)

                } else {
                    _stateListAdds.postValue(MyAddViewState.Empty)
                }
            } catch (e: Exception) {
                _stateListAdds.postValue(MyAddViewState.Error)
            }
        }

    }
    var searchType:String?=null
}