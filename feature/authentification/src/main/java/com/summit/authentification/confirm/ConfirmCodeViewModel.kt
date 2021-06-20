package com.summit.authentification.confirm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.summit.core.network.model.SignIn
import com.summit.core.network.repository.AuthRepository
import com.summit.core.network.repository.UserRepository
import kotlinx.coroutines.launch

class ConfirmCodeViewModel(private val repoAuth: AuthRepository, private val repoUser: UserRepository) : ViewModel() {
    lateinit var codeArgs: String
    lateinit var phoneArgs: String
    lateinit var identificador: String
    val user = repoUser.getUserTimeReal()

    private val _stateCode = MutableLiveData<ConfirmCodeViewState>()
    val stateCode: LiveData<ConfirmCodeViewState> get() = _stateCode


    private lateinit var _dataCode: String
    private val dataCode get() = _dataCode

    fun updateTextNumberCode(Code: CharSequence) {
        if (Code.isNotEmpty()) {
            if (Code.length > 5) {
                _stateCode.postValue(ConfirmCodeViewState.CodeSucces)
                _dataCode = Code.toString()
            } else {
                _stateCode.postValue(ConfirmCodeViewState.CodeError)
            }
        } else {
            _stateCode.postValue(ConfirmCodeViewState.CodeEmpty)
        }
    }

    private val _stateConfirmCode = MutableLiveData<ConfirmCodeViewState>()
    val stateConfirmCode: LiveData<ConfirmCodeViewState> get() = _stateConfirmCode

    fun sendCodeConfirmAuth() {
        viewModelScope.launch {
            _stateConfirmCode.postValue(ConfirmCodeViewState.Loading)
            try {
                val signinModel =
                    SignIn(code = codeArgs.replace("+", ""), numberPhone = phoneArgs, codeRecibe = dataCode)
                var idOrNull = repoAuth.sendVerifyCode(signinModel)

                if (idOrNull.isNullOrEmpty()) {
                    idOrNull = repoAuth.createUserVerifyCode(signinModel)
                }
                //Inserta el usuario en caso exista y en caso no se ira a la siguiente ventana de registro
                repoAuth.getDataInformation(idOrNull).apply {
                    if (this != null) {
                        repoUser.insertUser(this)
                        _stateConfirmCode.postValue(ConfirmCodeViewState.Complete)
                    } else {
                        identificador=idOrNull
                        _stateConfirmCode.postValue(ConfirmCodeViewState.InComplete)
                    }
                }



            } catch (e: Exception) {
                repoUser.deleteUser()
                _stateConfirmCode.postValue(ConfirmCodeViewState.Error)
            }
        }
    }


}