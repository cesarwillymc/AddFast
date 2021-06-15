package com.summit.authentification.login

import com.summit.commons.ui.base.BaseViewState


sealed class LoginViewState : BaseViewState {


    object Loading : LoginViewState()


    object Error : LoginViewState()


    object Complete : LoginViewState()

    object Empty : LoginViewState()


    fun isLoading() = this is Loading


    fun isError() = this is Error


    fun isComplete() = this is Complete

    fun isEmpty() = this is Empty
}
