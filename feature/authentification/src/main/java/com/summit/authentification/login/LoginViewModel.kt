package com.summit.authentification.login

import android.util.Log
import androidx.lifecycle.*
import com.summit.core.network.repository.AuthRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LoginViewModel(private val repoAuth:AuthRepository) : ViewModel() {
    private val _statePhone = MutableLiveData<LoginViewState>()
    val statePhone: LiveData<LoginViewState> get() = _statePhone


    private lateinit var _dataPhone :String
    val dataPhone get() = _dataPhone
    fun updateTextNumberPhone(phone: CharSequence) {
        if (phone.isNotEmpty()) {
            if (phone.length > 7) {
                _statePhone.postValue(LoginViewState.PhoneSucces)
                _dataPhone= phone.toString()
            } else {
                _statePhone.postValue(LoginViewState.PhoneError)
            }
        } else {
            _statePhone.postValue(LoginViewState.PhoneEmpty)
        }
    }


    private val _stateCode = MutableLiveData<LoginViewState>()
    val stateCode: LiveData<LoginViewState> get() = _stateCode

    private lateinit var _dataCode:String
    val dataCode get() = _dataCode
    fun updateTextCodeCountryPhone(code: CharSequence) {
        if (code.isNotEmpty()) {
            if (code.length <=2) {
                _stateCode.postValue(LoginViewState.CodeSucces)
                _dataCode = code.toString()
            } else {
                _stateCode.postValue(LoginViewState.CodeError)
            }
        } else {
            _stateCode.postValue(LoginViewState.CodeEmpty)
        }
    }

    private val _stateLogin = MutableLiveData<LoginViewState>()
    val stateLogin: LiveData<LoginViewState> get() = _stateLogin
    fun sendMessageLogged() {
        viewModelScope.launch {
            Log.e("login","entro ")

            _stateLogin.postValue(LoginViewState.Loading)
            try {
                repoAuth.sendNumberCode(_dataCode, _dataPhone)
                _stateLogin.postValue(LoginViewState.Complete)
            } catch (e: Exception) {
                Log.e("login","error ")
                Log.e("login","${e.message}")
                _stateLogin.postValue(LoginViewState.Error)
            }
        }
    }
    fun onStopViewModel(){
        viewModelScope.cancel()
    }
    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}