package com.summit.authentification.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _statePhone = MutableLiveData<LoginViewState>()
    val statePhone: LiveData<LoginViewState> get() = _statePhone


    private lateinit var _dataPhone :String
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

}