package com.summit.authentification.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.summit.core.network.repository.AuthRepository

class RegisterViewModel(private val repoAuth: AuthRepository) : ViewModel() {
    private val _stateRuc = MutableLiveData<RegisterViewState>()
    val stateRuc: LiveData<RegisterViewState> get() = _stateRuc


    private lateinit var _dataRuc: String
    val dataRuc get() = _dataRuc
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
    val dataDni get() = _dataDni
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
    val dataName get() = _dataName
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
    val dataBussiness get() = _dataBussiness
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

    fun onCheckedButton(value:Boolean){
        _dataOnChecked.postValue(value)
    }

}