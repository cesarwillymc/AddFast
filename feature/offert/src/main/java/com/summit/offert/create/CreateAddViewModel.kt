package com.summit.offert.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.summit.core.network.model.Anuncios
import com.summit.core.network.model.CategoriasModel
import com.summit.core.network.model.departamento.ProvinciaItem
import com.summit.core.network.model.departamento.UbiModel

import com.summit.core.network.repository.AdRepository
import com.summit.core.network.repository.CategoryRepository
import com.summit.core.network.repository.GpsRepository
import com.summit.core.network.repository.UserRepository
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class CreateAddViewModel(
    private val adRepo: AdRepository,
    private val gpsRepo: GpsRepository,
    private val userRepo: UserRepository,
    private val categoryRepo: CategoryRepository
) : ViewModel() {
    var positionCategory=0
    var positionProvince=0
    var positionDepartament=0

    fun initViewModel(){
        if(dataProvincia.value==null&&dataDepartamento.value==null){
            getDepartamentos()
            getCategorias()
        }

    }

    private val _stateTitle = MutableLiveData<CreateAddViewState>()
    val stateTitle: LiveData<CreateAddViewState> get() = _stateTitle

    private lateinit var _dataTitle: String
    private val dataTitle get() = _dataTitle
    fun updateTextNumberTitle(word: CharSequence) {
        if (word.isNotEmpty()) {
            if (word.length > 5) {
                _stateTitle.postValue(CreateAddViewState.TitleSucces)
                _dataTitle = word.toString()
            } else {
                _stateTitle.postValue(CreateAddViewState.TitleError)
            }
        } else {
            _stateTitle.postValue(CreateAddViewState.TitleEmpty)
        }
    }

    private val _stateDesc = MutableLiveData<CreateAddViewState>()
    val stateDesc: LiveData<CreateAddViewState> get() = _stateDesc

    private lateinit var _dataDesc: String
    private val dataDesc get() = _dataDesc
    fun updateTextNumberDesc(word: CharSequence) {
        if (word.isNotEmpty()) {
            if (word.length > 10) {
                _stateDesc.postValue(CreateAddViewState.DescSucces)
                _dataDesc = word.toString()
            } else {
                _stateDesc.postValue(CreateAddViewState.DescError)
            }
        } else {
            _stateDesc.postValue(CreateAddViewState.DescEmpty)
        }
    }


    val photoDirection = MutableLiveData<File>()

    val typeAdd = MutableLiveData<List<String>>()


    val departamentData = MutableLiveData<String>()

    private val _dataDepartamento = MutableLiveData<List<ProvinciaItem>>()
    val dataDepartamento: LiveData<List<ProvinciaItem>> get() = _dataDepartamento

    private val _dataProvincia = MutableLiveData<List<ProvinciaItem>>()
    val dataProvincia: LiveData<List<ProvinciaItem>> get() = _dataProvincia

    private fun getDepartamentos() {
        viewModelScope.launch {
            try {
                val resultado = gpsRepo.verDepartamento()
                if (resultado != null) {
                    _dataDepartamento.postValue(resultado)
                    getProvincias(resultado[0].id)
                } else {
                    _dataDepartamento.postValue(listOf())
                }

            } catch (e: Exception) {
            }
        }
    }

    fun getProvincias(id: String) {
        viewModelScope.launch {
            try {
                val resultado = gpsRepo.verProvincia(id)
                if (resultado != null) {
                    positionProvince=0
                    _dataProvincia.postValue(resultado)
                } else {
                    _dataProvincia.postValue(listOf())
                }

            } catch (e: Exception) {
            }
        }
    }


    private val _stateRegister = MutableLiveData<CreateAddViewState>()
    val stateRegister: LiveData<CreateAddViewState> get() = _stateRegister


    fun createAdd() {
        viewModelScope.launch {
            _stateRegister.postValue(CreateAddViewState.Loading)
            try {
                val user = userRepo.getUserStatic()!!
                val urlanuncio = adRepo.uploadFotoAnuncio(photoDirection.value!!)
                val model = Anuncios(
                    descripcion = dataDesc, fecha = Date().time,
                    id = Date().time.toString(), idempresa = user._id,
                    idusuario = user._id, img = urlanuncio,
                    phone = user.phone!!, ubicacion = UbiModel(),
                    type = typeAdd.value!![0], typeID = typeAdd.value!![1],
                    titulo = dataTitle, postulaciones = listOf(),
                    reporte = 0, visualizaciones = 0, estado = "PUBLICADO"
                )
                adRepo.crearAnuncio(model, departamentData.value!!, dataProvincia.value!![positionProvince].name)
                _stateRegister.postValue(CreateAddViewState.Complete)
            } catch (e: Exception) {
                _stateRegister.postValue(CreateAddViewState.Error)
            }
        }
    }

    private val _stateCategory = MutableLiveData<CreateAddViewState>()
    val stateCategory: LiveData<CreateAddViewState> get() = _stateCategory

    private var _dataCategory = listOf<CategoriasModel>()
    val dataCategory: List<CategoriasModel> get() = _dataCategory

    fun  getCategorias()  {
        viewModelScope.launch {
            _stateCategory.postValue(CreateAddViewState.Loading)
            try {
                val resultado=categoryRepo.getAllCategorias()
                _dataCategory=(resultado)
                _stateCategory.postValue( CreateAddViewState.Complete)

            }catch (e:Exception){
                _stateCategory.postValue(CreateAddViewState.Error)
            }
        }

    }
}