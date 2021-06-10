package com.summit.home.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.summit.core.network.model.Anuncios
import com.summit.core.network.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repoCate:CategoryRepository
) : ViewModel() {

    private val _state = MutableLiveData<CategoryViewState>()
    val state: LiveData<CategoryViewState> get() = _state

    private val _data = MutableLiveData<List<Anuncios>>()
    val data: LiveData<List<Anuncios>> get() = _data

    fun  getAllAnunciosByCategorias(id: String) {
        _state.postValue(CategoryViewState.Loading)
        viewModelScope.launch {
            try {
                val resultado=repoCate.getAllAnunciosByCategorias(id)
                if(resultado.isNotEmpty()){
                    _data.postValue(resultado)
                    _state.postValue(CategoryViewState.Complete)
                }else{
                    _state.postValue(CategoryViewState.Empty)
                }
            }catch (e:Exception){
                _state.postValue(CategoryViewState.Error)
            }

        }
    }

}