package com.summit.home.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.summit.core.network.model.CategoriasModel
import com.summit.core.network.model.ListaAnuncios
import com.summit.core.network.model.Promociones
import com.summit.core.network.repository.CategoryRepository
import com.summit.core.network.repository.GpsRepository
import com.summit.core.network.repository.OfferRepository
import com.summit.home.home.state.HomeViewState
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repoUbi: GpsRepository,
    private val repoPromo: OfferRepository,
    private val repoCategory: CategoryRepository
) : ViewModel() {

    val getUbicacion = repoUbi.getUbicacion()

    private val _dataPromociones = MutableLiveData<List<Promociones>>()
    val dataPromociones: LiveData<List<Promociones>>
        get() = _dataPromociones

    private val _statePromociones = MutableLiveData<HomeViewState>()
    val statePromociones: LiveData<HomeViewState>
        get() = _statePromociones

    private val _dataAnuncios = MutableLiveData<List<ListaAnuncios>>()
    val dataAnuncios: LiveData<List<ListaAnuncios>>
        get() = _dataAnuncios

    private val _stateAnuncios = MutableLiveData<HomeViewState>()
    val stateAnuncios: LiveData<HomeViewState>
        get() = _stateAnuncios

   private val _dataCategorys = MutableLiveData<List<CategoriasModel>>()
   val dataCategorys: LiveData<List<CategoriasModel>>
      get() = _dataCategorys

   private val _stateCategorys = MutableLiveData<HomeViewState>()
   val stateCategorys: LiveData<HomeViewState>
      get() = _stateCategorys

    fun getPromocionesUpdate() {
        _statePromociones.postValue(HomeViewState.Loading)
        viewModelScope.launch {
            try {
                val response = repoPromo.getPromocion()
                if(response.isNotEmpty()){
                    _dataPromociones.postValue(response)
                    _statePromociones.postValue(HomeViewState.Complete)
                }else{
                    _statePromociones.postValue(HomeViewState.Empty)
                }

            } catch (e: Exception) {
                _statePromociones.postValue(HomeViewState.Error)
            }
        }
    }

    fun getAnunciosByCategorias() {
        _stateAnuncios.postValue(HomeViewState.Loading)
        viewModelScope.launch {
            try {
                val response = repoCategory.getAllCategorias()
                val listado: MutableList<ListaAnuncios> = mutableListOf()
                var dato = 0
                response.forEach {
                    val waiting = repoCategory.getAnunciosByCategorias(it.id)
                    if (waiting.isNotEmpty()) {
                        listado.add(ListaAnuncios(it.id, it.name, waiting))
                    }
                    if (dato == response.size - 1) {
                        if (listado.isNotEmpty()) {
                            _dataAnuncios.postValue(listado)
                            _stateAnuncios.postValue(HomeViewState.Complete)
                        } else {
                            _stateAnuncios.postValue(HomeViewState.Empty)
                        }
                    }
                    dato++
                }
            } catch (e: Exception) {
                _stateAnuncios.postValue(HomeViewState.Error)
            }
        }
    }

   fun getCategorias() {
      _stateCategorys.postValue(HomeViewState.Loading)
      viewModelScope.launch {
         try {
            val response = repoCategory.getAllCategorias()

            _dataCategorys.postValue(response)
            _stateCategorys.postValue(HomeViewState.Complete)
         } catch (e: Exception) {
            _stateCategorys.postValue(HomeViewState.Error)
         }
      }
   }

}
