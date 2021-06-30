package com.summit.authentification.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.summit.core.network.model.Usuario
import com.summit.core.network.repository.AuthRepository
import com.summit.core.network.repository.UserRepository
import kotlinx.coroutines.launch
import java.io.File

class RegisterViewModel(private val repoAuth: AuthRepository, private val repoUser: UserRepository) : ViewModel() {
    /** Args **/
    lateinit var phoneArgs: String
    lateinit var idArgs: String

    private val _stateRuc = MutableLiveData<RegisterViewState>()
    val stateRuc: LiveData<RegisterViewState> get() = _stateRuc

    private lateinit var _dataRuc: String
    private val dataRuc get() = _dataRuc
    fun updateTextNumberRuc(word: CharSequence) {
        if (word.isNotEmpty()) {
            if (word.length > 10) {
                _stateRuc.postValue(RegisterViewState.RucSucces)
                _dataRuc = word.toString()
            } else {
                _stateRuc.postValue(RegisterViewState.RucError)
            }
        } else {
            _stateRuc.postValue(RegisterViewState.RucEmpty)
        }
    }

    private val _stateDni = MutableLiveData<RegisterViewState>()
    val stateDni: LiveData<RegisterViewState> get() = _stateDni


    private lateinit var _dataDni: String
    private val dataDni get() = _dataDni
    fun updateTextNumberDni(Dni: CharSequence) {
        if (Dni.isNotEmpty()) {
            if (Dni.length > 7) {
                _stateDni.postValue(RegisterViewState.DniSucces)
                _dataDni = Dni.toString()
            } else {
                _stateDni.postValue(RegisterViewState.DniError)
            }
        } else {
            _stateDni.postValue(RegisterViewState.DniEmpty)
        }
    }

    private val _stateName = MutableLiveData<RegisterViewState>()
    val stateName: LiveData<RegisterViewState> get() = _stateName


    private lateinit var _dataName: String
    private val dataName get() = _dataName
    fun updateTextNumberName(Name: CharSequence) {
        if (Name.isNotEmpty()) {
            if (Name.length > 3) {
                _stateName.postValue(RegisterViewState.NameSucces)
                _dataName = Name.toString()
            } else {
                _stateName.postValue(RegisterViewState.NameError)
            }
        } else {
            _stateName.postValue(RegisterViewState.NameEmpty)
        }
    }

    private val _stateBussiness = MutableLiveData<RegisterViewState>()
    val stateBussiness: LiveData<RegisterViewState> get() = _stateBussiness


    private lateinit var _dataBussiness: String
    private val dataBussiness get() = _dataBussiness
    fun updateTextNumberBussiness(Bussiness: CharSequence) {
        if (Bussiness.isNotEmpty()) {
            if (Bussiness.length > 3) {
                _stateBussiness.postValue(RegisterViewState.BussinessSucces)
                _dataBussiness = Bussiness.toString()
            } else {
                _stateBussiness.postValue(RegisterViewState.BussinessError)
            }
        } else {
            _stateBussiness.postValue(RegisterViewState.BussinessEmpty)
        }
    }

    private val _dataOnChecked = MutableLiveData(false)
    val dataOnChecked: LiveData<Boolean> get() = _dataOnChecked

    fun onCheckedButton(value: Boolean) {
        _dataOnChecked.postValue(value)
    }

    var photoDirection = MutableLiveData<File>()

    private val _stateRegister = MutableLiveData<RegisterViewState>()
    val stateRegister: LiveData<RegisterViewState> get() = _stateRegister
    fun registerInformationUser() {
        viewModelScope.launch {
            Log.e("entro","register info")
            _stateRegister.postValue(RegisterViewState.Loading)
            try {
                val responsePhoto = repoAuth.uploadImageProfile(imagen = photoDirection.value!!)
                val urlPath = repoUser.getUrlDownloadFile(responsePhoto)
                val modelUser =
                    Usuario(dataName, dataDni, phoneArgs, dataBussiness, dataRuc, urlPath, admin = false, _id = idArgs)
                val responseCreate = repoAuth.createDataInformation(
                    idArgs,
                    modelUser
                )
                if (responseCreate) {
                    repoUser.insertUser(modelUser)
                    _stateRegister.postValue(RegisterViewState.Complete)
                } else {
                    _stateRegister.postValue(RegisterViewState.Error)
                }

            } catch (e: Exception) {
                _stateRegister.postValue(RegisterViewState.Error)
            }
        }
    }

}