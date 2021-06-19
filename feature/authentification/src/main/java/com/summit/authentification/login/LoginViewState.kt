package com.summit.authentification.login

import com.summit.commons.ui.base.BaseViewState


sealed class LoginViewState : BaseViewState {

    /** Edit Text State send info login**/
    object Loading : LoginViewState()

    object Complete : LoginViewState()

    object Error : LoginViewState()


    fun isLoading() = this is Loading

    fun isComplete() = this is Complete

    fun isError() = this is Error


    /** Edit Text State phone **/
    object PhoneEmpty : LoginViewState()
    object PhoneError : LoginViewState()
    object PhoneSucces : LoginViewState()

    fun isPhoneEmpty() = this is PhoneEmpty
    fun isPhoneError() = this is PhoneError
    fun isPhoneSucces() = this is PhoneSucces

    /** Edit Text State code country **/
    object CodeEmpty : LoginViewState()
    object CodeError : LoginViewState()
    object CodeSucces : LoginViewState()

    fun isCodeEmpty() = this is CodeEmpty

    fun isCodeError() = this is CodeError

    fun isCodeSucces() = this is CodeSucces

}
