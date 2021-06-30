package com.summit.postulateadd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.summit.core.network.model.Anuncios
import com.summit.core.network.model.Postulacion
import com.summit.core.network.repository.PostulateRepository
import com.summit.core.network.repository.UserRepository
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class PostulateAddViewModel(
    private val postulateRepo: PostulateRepository,
    private val userRepo: UserRepository
):ViewModel() {

    val anuncioArgs =MutableLiveData<Anuncios>()


    private val _stateEspeciality = MutableLiveData<PostulateAddViewState>()
    val stateEspeciality: LiveData<PostulateAddViewState> get() = _stateEspeciality

    private lateinit var _dataEspeciality: String
    private val dataEspeciality get() = _dataEspeciality
    fun updateTextEspeciality(word: CharSequence) {
        if (word.isNotEmpty()) {
            if (word.length > 5) {
                _stateEspeciality.postValue(PostulateAddViewState.EspecialitySucces)
                _dataEspeciality = word.toString()
            } else {
                _stateEspeciality.postValue(PostulateAddViewState.EspecialityError)
            }
        } else {
            _stateEspeciality.postValue(PostulateAddViewState.EspecialityEmpty)
        }
    }


    private val _stateResume = MutableLiveData<PostulateAddViewState>()
    val stateResume: LiveData<PostulateAddViewState> get() = _stateResume

    private lateinit var _dataResume: String
    private val dataResume get() = _dataResume
    fun updateTextResume(word: CharSequence) {
        if (word.isNotEmpty()) {
            if (word.length > 5) {
                _stateResume.postValue(PostulateAddViewState.ResumeSucces)
                _dataResume = word.toString()
            } else {
                _stateResume.postValue(PostulateAddViewState.ResumeError)
            }
        } else {
            _stateResume.postValue(PostulateAddViewState.ResumeEmpty)
        }
    }

    private val _stateDifference = MutableLiveData<PostulateAddViewState>()
    val stateDifference: LiveData<PostulateAddViewState> get() = _stateDifference

    private lateinit var _dataDifference: String
    private val dataDifference get() = _dataDifference
    fun updateTextDifference(word: CharSequence) {
        if (word.isNotEmpty()) {
            if (word.length > 5) {
                _stateDifference.postValue(PostulateAddViewState.DifferenceSucces)
                _dataDifference = word.toString()
            } else {
                _stateDifference.postValue(PostulateAddViewState.DifferenceError)
            }
        } else {
            _stateDifference.postValue(PostulateAddViewState.DifferenceEmpty)
        }
    }

    private val _statePhone = MutableLiveData<PostulateAddViewState>()
    val statePhone: LiveData<PostulateAddViewState> get() = _statePhone

    private lateinit var _dataPhone: String
    private val dataPhone get() = _dataPhone
    fun updateTextNumberPhone(word: CharSequence) {
        if (word.isNotEmpty()) {
            if (word.length > 5) {
                _statePhone.postValue(PostulateAddViewState.PhoneSucces)
                _dataPhone = word.toString()
            } else {
                _statePhone.postValue(PostulateAddViewState.PhoneError)
            }
        } else {
            _statePhone.postValue(PostulateAddViewState.PhoneEmpty)
        }
    }

    val dataFile= MutableLiveData<File>()

    private val _stateRegister = MutableLiveData<PostulateAddViewState>()
    val stateRegister: LiveData<PostulateAddViewState> get() = _stateRegister


    fun  crearPostulacion()  {
        viewModelScope.launch {
            _stateRegister.postValue(PostulateAddViewState.Loading)
            try {
                val user= userRepo.getUserStatic()!!
                val path=postulateRepo.uploadCurriculumPostulacion(dataFile.value!!)
                val model= Postulacion(
                    diferencia = dataDifference, estado = "ENVIADO",
                    fecha = Date().time, id = "", idanuncio = anuncioArgs.value!!.id,
                    idempresa = anuncioArgs.value!!.idempresa, idpostulante = user._id,
                    titulo= anuncioArgs.value!!.titulo, descripcion= anuncioArgs.value!!.descripcion,
                    name=user.name!!, phonepostulante= dataPhone, rcv=dataResume, archivopdf= path,
                    imgpostulante = user.uriImgPerfil!!, respecialidad = dataEspeciality, imganuncio = anuncioArgs.value!!.img
                )

                val resultado=postulateRepo.crearPostulacion(model)
                postulateRepo.postularAnuncio(model.idanuncio,resultado)
                _stateRegister.postValue( PostulateAddViewState.Complete)

            }catch (e:Exception){
                _stateRegister.postValue(PostulateAddViewState.Error)
            }
        }
    }
}